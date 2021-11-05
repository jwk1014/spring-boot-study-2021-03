package com.example.demo;

import com.example.demo.model.mongoEntity.User;
import com.example.demo.model.req.RGithub;
import com.example.demo.mongoRepository.MongoUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient githubWebClient;

	@Test
	void contextLoads() {
		final ResponseEntity<List<RGithub.ListGetRepo>> res = restTemplate.exchange(
				"https://api.github.com/orgs/google/repos",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<RGithub.ListGetRepo>>(){});

		System.out.println("Status Code : " + res.getStatusCode());
		for(final RGithub.ListGetRepo repo : res.getBody()) {
			System.out.println(repo.getId() + " / " + repo.getName());
		}
	}

	@Test
	void contextLoads2() {
		List<RGithub.ListGetRepo> list = githubWebClient.get()
				.uri("/orgs/google/repos")
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<RGithub.ListGetRepo>>() {})
				.block();
		for(final RGithub.ListGetRepo repo : list) {
			System.out.println(repo.getId() + " / " + repo.getName());
		}
	}

	@Autowired
	MongoUserRepository userRepository;

	@Test
	void findMongoRepository() {
		List<User> userList = userRepository.findUserByName("정원");

		for(User user : userList) {
			System.out.println(user.getId() + " / " + user.getName());
		}
	}

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Test
	void produceKafka() {
		kafkaTemplate.send("spring-boot-study", "{\"age\":32}")
				.addCallback(result -> {
					System.out.println("성공");
				},t -> {
					System.err.println(t.getLocalizedMessage());
				});
	}
}
