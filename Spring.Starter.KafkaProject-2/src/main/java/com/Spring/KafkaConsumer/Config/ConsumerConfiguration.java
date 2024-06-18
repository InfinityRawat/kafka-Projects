package com.Spring.KafkaConsumer.Config;

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
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;


@Configuration
public class ConsumerConfiguration {

	 	@Bean
	     Map<String, Object> consumerConfig() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
	                "localhost:9092");
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
	                StringDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
	                JsonDeserializer.class);
	        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.Spring.KafkaConsumer.Entity");
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "grp1");
			props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.Spring.KafkaConsumer.Entity.Customer");
			props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.Spring.KafkaConsumer.Entity");
			props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

	        return props;
	    }


	@Bean
	ConsumerFactory<String, Object> factory(){

		ConsumerFactory<String, Object> fact = new DefaultKafkaConsumerFactory<>(consumerConfig());
		return fact;
	}

	 @Bean
	     KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
	                new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(factory());
	        return factory;
	    }


}
