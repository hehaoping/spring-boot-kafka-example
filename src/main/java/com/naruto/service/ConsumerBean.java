package com.naruto.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.naruto.dao.VisitLogMapper;
import com.naruto.entity.VisitLog;

/**
 * @author hhp
 * @mail 1228929031@qq.com
 * @date 2018年4月21日
 */
@Component
public class ConsumerBean {

	@Value("${my.kafka.topic}")
	private String kafkaTopic;

	// 简单单一消费
	// @KafkaListener(topics="${my.kafka.topic}")
	// public void receive(ConsumerRecord<?, ?> cr){
	// String topic = cr.topic();
	// String key = cr.key().toString();
	// String value = cr.value().toString();
	// System.out.println("消费了消息：");
	// System.out.println("topic:"+topic+" key:"+key+" value:"+value);
	// }

	// 批量消费
	@KafkaListener(topics = "${my.kafka.topic}", containerFactory = "batchContainerFactory")
	public void listen(List<ConsumerRecord<?, ?>> crList, Acknowledgment ack) {
		System.out.println("records.size: " + crList.size() + " in all");
		boolean status = batchAddlog(crList);
		if (status) {
			System.out.println("start commit offset");
			ack.acknowledge();// 手动提交偏移量
			System.out.println("stop commit offset");
		}
	}

	@Autowired
	private VisitLogMapper visitLogMapper;
	

	@Transactional
	private boolean batchAddlog(List<ConsumerRecord<?, ?>> crList) {
		try {
			for (ConsumerRecord<?, ?> cr : crList) {
				String key = cr.key().toString();
				String value = cr.value().toString();
				JSONObject jo = JSONObject.parseObject(value);

				VisitLog log = new VisitLog();
				log.setId(key);
				log.setTime(jo.getIntValue("time"));
				log.setIp(jo.getString("ip"));
				log.setBrowser(jo.getString("browserName"));
				log.setOs(jo.getString("OS"));
				visitLogMapper.add(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
