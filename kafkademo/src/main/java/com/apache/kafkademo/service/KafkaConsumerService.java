package com.apache.kafkademo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

	private ElasticSearchService elastic;

	
	@Autowired
	public KafkaConsumerService(ElasticSearchService elastic) {
		this.elastic=elastic;
	}
	
	@KafkaListener(topics = "first_topic", groupId = "first_application")
	public void listenerGroupFirstApplication(@Payload String message) {
		System.out.println("Message Consumed : " + message);
		try {
			elastic.sendMessageToElasticSearch(message);
		} catch (IOException e) {
			System.out.println("error encoutered");
			e.printStackTrace();
		}
		// manually commit offset
//		if (value % 5 == 0) {
//			acknowledgment.acknowledge();
//		}
		//bulk request

	}
}
