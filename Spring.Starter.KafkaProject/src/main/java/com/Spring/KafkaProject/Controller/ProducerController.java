package com.Spring.KafkaProject.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Spring.KafkaProject.Entity.Customer;
import com.Spring.KafkaProject.Services.ProducerService;

@Controller
@RequestMapping("/producer")
public class ProducerController {
		
	private ProducerService service;

	public ProducerController(ProducerService service) {
		this.service = service;
	}
	
	@GetMapping("/send/{message}")
	public ResponseEntity<?> sendMessage(@PathVariable String message)
	{
		try {
		service.sendMessage(message);
		return ResponseEntity.ok(message);

		}
		catch(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GetMapping("/sendCustomer")
	public ResponseEntity<?> sendCustomerData(@RequestBody Customer customer ){
		
		try {
			
				service.sendCustomer(customer);
			return ResponseEntity.ok(customer);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
