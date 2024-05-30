package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReceiptsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiptsApplication.class, args);

		System.out.println("http://localhost:8080/api/foods - список продуктов");
		System.out.println("http://localhost:8080/api/ingredients - список ингредиентов");
		System.out.println("http://localhost:8080/api/matches - список соответствий");
		System.out.println("http://localhost:8080/api/mealtimes - список типов приёмов пищи");
		System.out.println("http://localhost:8080/api/receipts - список рецептов");
		System.out.println("http://localhost:8080/api/servings - список порций");
	}

}
