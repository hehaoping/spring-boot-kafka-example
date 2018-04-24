package com.naruto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author hhp
 * @mail 1228929031@qq.com
 * @date 2018年4月21日
 */

@Component
public class ProducerBean {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${my.kafka.topic}")
    private String kafkaTopic;

	//简单发送消息
	public void sendMessage(String key, String data) {
		kafkaTemplate.send(kafkaTopic, key, data);
		System.out.println("生产了消息:"+key);
	}

}
