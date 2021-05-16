package com.apache.kafkademo.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Bean
	public ConsumerFactory<String, String> getConsumerFactory(){
		Map<String, Object> properties = new HashMap<>();
		
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "first_application");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		//
	//	properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); //disable auto commit offset
//		properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10"); //fetch max 10 records at a time.
		return new DefaultKafkaConsumerFactory<>(properties);
	}
	
	@Bean 
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaConsumerListenerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory =
		          new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(getConsumerFactory());
		return factory;
	}

}
