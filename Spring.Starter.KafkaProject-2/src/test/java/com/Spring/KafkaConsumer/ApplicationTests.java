package com.Spring.KafkaConsumer;

import com.Spring.KafkaConsumer.Entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Slf4j
class ApplicationTests {

	@Container
	static KafkaContainer container = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka"));


	@DynamicPropertySource
	public static void dynamicProp(DynamicPropertyRegistry registry){

		registry.add("spring.kafka.bootstrap-servers", container::getBootstrapServers);
	}

	@Autowired
	public KafkaTemplate<String,Object> template;

	@Test
	public  void testCustomerConsumer(){
		log.info("Test started..........");
		template.send("JavaTech",new Customer(1,"test"));
		log.info("Test Ended.........");
		await().pollInterval(Duration.ofSeconds(5)).atMost(10, TimeUnit.SECONDS).untilAsserted(()
		->{}
		);
	}
}
