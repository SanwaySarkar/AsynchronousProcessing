package com.asp.kafka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.annotation.EnableKafka;




@SpringBootApplication
@EnableKafka
@ComponentScan("com.asp")
@EnableRedisRepositories
public class KafkaConsumerStarterApplication {
	

	
	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerStarterApplication.class, args);	
	}
	
	
	

}
