package com.apache.kafkademo.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		String bootstrapUrl = "127.0.0.1:9092";
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapUrl);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		//enable idempotent producer.
		//configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
		//teru for ack = 1 ie. leader only acknowledgment. 
	//	configProps.put(ProducerConfig.ACKS_CONFIG, "all");
		
		//high throughput setting
		//google compression -> compresses text and json messages.
	//	configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
	//	configProps.put(ProducerConfig.LINGER_MS_CONFIG, "20");
	//	configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); //32kb
		return new DefaultKafkaProducerFactory<>(configProps);
		
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

//	@Bean
//	public KafkaProducer<String, String> producerFactory(){
//		//Map<String, String> properties = new HashMap<>();
//		Properties properties = new Properties();
//		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		
//		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
//		return producer;
//	}

//	@Bean	
//    public KafkaTemplate<String, String> kafkaTemplate() {
//		
//		KafkaProducer<String, String> producer = producerFactory();
//		
//		KafkaTemplate<String, String> kafka = new KafkaTemplate<String, String>(producer);
//      //  return new KafkaTemplate<String, String>(producerFactory());
//    }
}
