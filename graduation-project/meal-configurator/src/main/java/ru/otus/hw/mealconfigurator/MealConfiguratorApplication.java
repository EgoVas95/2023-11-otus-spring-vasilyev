package ru.otus.hw.mealconfigurator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
@EnableDiscoveryClient
public class MealConfiguratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealConfiguratorApplication.class, args);
	}

}
