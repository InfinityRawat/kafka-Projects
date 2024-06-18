package com.Spring.KafkaProject.Config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public class ProducerTopicConfig {


			@Bean
			 NewTopic newTopic() {
				NewTopic topic =  new NewTopic("JavaTech",3,(short)1);

				return topic;
			}
			@Bean
			 Map<String,Object> propConfig(){

				Map<String,Object> map = new HashMap<>();

				map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
				map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
				map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
				return map;
			}

			@Bean
			ProducerFactory<String, Object> producerFactory(){
				ProducerFactory<String, Object> fact = new DefaultKafkaProducerFactory<String, Object>(propConfig());
				return fact;
			}

			@Bean
			KafkaTemplate<String,Object> kafkaTemplate(){
				KafkaTemplate<String,Object> temp = new KafkaTemplate<>(producerFactory());
				return temp;
			}

}
