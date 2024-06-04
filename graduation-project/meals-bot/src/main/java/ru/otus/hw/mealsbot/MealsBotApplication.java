package ru.otus.hw.mealsbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MealsBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealsBotApplication.class, args);
	}

}
