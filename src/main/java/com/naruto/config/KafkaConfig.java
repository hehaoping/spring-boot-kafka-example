package com.naruto.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer.AckMode;

/**
 * @author hhp
 * @mail 1228929031@qq.com
 * @date 2018年4月21日
 */

@Configuration
public class KafkaConfig {

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory() {
		Map<String, Object> configs = new HashMap<String, Object>(); // 参数
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "myGroup");
		configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		configs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100"); // 批量消费数量
		configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		return new DefaultKafkaConsumerFactory<Object, Object>(configs);
	}


	/**
	 * 添加KafkaListenerContainerFactory，用于批量消费消息
	 * 
	 * @return
	 */
	@Bean
	public KafkaListenerContainerFactory<?> batchContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Object, Object> containerFactory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		containerFactory.setConsumerFactory(consumerFactory());
		containerFactory.setConcurrency(4);
		containerFactory.setBatchListener(true); // 设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
		containerFactory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);// 设置提交偏移量的方式，故 需要在消费监听的时候，手动提交偏移量
		return containerFactory;
	}

}
