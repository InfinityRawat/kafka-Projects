package com.Spring.KafkaConsumer.ConsumerServices;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;

import org.springframework.stereotype.Service;

import com.Spring.KafkaConsumer.Entity.Customer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerService {
	
//	@KafkaListener(topics =  "JavaTech",groupId= "grp1")
//	public void consume(String msg) {
//		
//		log.info(msg);
//	}
//	
//	@KafkaListener(topics =  "JavaTech",groupId= "grp1")
//	public void consume2(String msg) {
//		
//		log.info(msg);
//	}
//	@KafkaListener(topics =  "JavaTech1",groupId= "grp1")
//	public void consume3(String msg) {
//		
//		log.info(msg);
//	}
//	
//	@KafkaListener(topics = "JavaTech",groupId="grp1")
//	public void customerConsumer(Customer customer) {
//		log.info("Consumer information -------------------------------");
//		System.out.println(customer.toString());
//	}
	
    @RetryableTopic(attempts = "4")// 3 topic N-1
	@KafkaListener(topics = "RetryDemo", groupId="grp1")
	public void consumeEvent(Customer cust) {
			
		log.info("consumer started for customer Event");
		List<String> of = Stream.of("A","B","C","D").toList();
		
		if(of.contains(cust.getName())) {
			throw new RuntimeException("Exception come in this line");
		}
		
		System.out.println("this is consumer's message"+cust);
	}
	
	@DltHandler
	public void dltMethod(Customer cust) {
			
		log.info("this is the cust "+cust );
	}

}
