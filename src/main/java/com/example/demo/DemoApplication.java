package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class,
		org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class,
		org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration.class
})
@EnableAspectJAutoProxy
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
