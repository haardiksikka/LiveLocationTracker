package com.apache.kafkademo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Service
public class KafkaProducerService {

	
	@Autowired KafkaTemplate<String, String> producer;
	
	public void sendMessage() {
		System.out.println("enter");
		producer.send("first_topic", "hello");
	}
	
	public Map<String, String> sendMessageWithCallback(String location) {
		ListenableFuture<SendResult<String, String>> future = producer.send("first_topic", location);
		
		Map<String, String> hm = new HashMap<String, String>(); 
		
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>(){

			@Override
			public void onSuccess(SendResult<String, String> result) {
					hm.put("topic", result.getRecordMetadata().topic());
					hm.put("partition", ""+result.getRecordMetadata().partition());
					hm.put("offset",""+result.getRecordMetadata().offset());
					hm.put("timestamp", ""+result.getRecordMetadata().timestamp());
					System.out.println("logs:"+hm);
			}

			@Override
			public void onFailure(Throwable ex) {
					hm.put("exception encoutered", ex.getMessage());
					throw new RuntimeException(ex.getMessage());
			}
			
		});
		System.out.println("exiting from producer method::");
		return hm;
	}
}
