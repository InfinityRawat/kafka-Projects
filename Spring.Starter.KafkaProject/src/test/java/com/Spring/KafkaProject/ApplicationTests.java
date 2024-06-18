package com.Spring.KafkaProject;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import com.Spring.KafkaProject.Entity.Customer;
import com.Spring.KafkaProject.Services.ProducerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ApplicationTests {

	@Container
	static KafkaContainer container = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka"));
	
	
	@DynamicPropertySource
	public static void dynamicProperties(DynamicPropertyRegistry registry) {
		
		registry.add("spring.kafka.bootstrap-servers", container::getBootstrapServers);

	}
	
	@Autowired
	private ProducerService publisher;
	
	
	@Test
	public void testSendEventsToTopic() {
		
		publisher.sendCustomer(Customer.builder().id(0).name("test").build());
		Awaitility.await().pollInterval(Duration.ofSeconds(3)).atMost(10,TimeUnit.SECONDS).untilAsserted(()->
		{}
		);
		
	}
	
//	@BeforeAll
//	public static void initKafkaProperties() {
//		container.start();
//	}
//	
//	@AfterAll
//	public static void beforeAll() {
//		container.stop();
//	}

}
