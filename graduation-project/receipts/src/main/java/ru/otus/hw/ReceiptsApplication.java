package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReceiptsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiptsApplication.class, args);

		System.out.println("http://localhost:8181/api/foods - список продуктов");
		System.out.println("http://localhost:8181/api/ingredients - список ингредиентов");
		System.out.println("http://localhost:8181/api/matches - список соответствий");
		System.out.println("http://localhost:8181/api/mealtimes - список типов приёмов пищи");
		System.out.println("http://localhost:8181/api/receipts - список рецептов");
		System.out.println("http://localhost:8181/api/servings - список порций");
	}

}
