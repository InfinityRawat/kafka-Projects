package com.Spring.KafkaProject.Services;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.Spring.KafkaProject.Entity.Customer;


@Service
public class ProducerService {
	
	
	private KafkaTemplate< String, Object> kafkaTemp;
	

	public ProducerService(KafkaTemplate<String, Object> kafkaTemp) {
		this.kafkaTemp = kafkaTemp;
	}
	
	public void sendMessage(String topic) {
		
		
		CompletableFuture<SendResult<String, Object>> future = kafkaTemp.send("JavaTech",topic);
		
		future.whenComplete((result,ex)-> {
			
			if(ex==null) {
				System.out.println(result + "  with offSet: "+ result.getRecordMetadata().offset());
			}
			else {
				System.out.println(ex.getStackTrace());
			}
		});
		
	}
		
	public void sendCustomer(Customer customer) {
		
		try {
		CompletableFuture<SendResult<String, Object>> future =kafkaTemp.send("RetryDemo",customer);
		
		future.whenComplete((result,ex)->{
			
			if(ex==null) {
				System.out.println(customer.toString() + "  with offSet: "+ result.getRecordMetadata().offset());

			}
			else {
				System.out.println("unable to send message " +customer.toString()+"  --> "+ex.getStackTrace());
			}
		});
	
	}
	catch(Exception ex) {
		System.out.println(ex.getMessage());
	}
}
	
	public void sendEvent(Customer customer) {
			
		CompletableFuture<SendResult<String, Object>> send = kafkaTemp.send("JavaTech",customer);
		send.whenComplete((res,ex)->{
							
				if(ex==null) {
					System.out.println(customer.toString() + "  with offSet: "+ res.getRecordMetadata().offset());

				}
				else {
					System.out.println("unable to send message " +customer.toString()+"  --> "+ex.getStackTrace());
				}
		});
		
	}
	


}
