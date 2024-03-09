package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("http://localhost:8080/api/books - список книг");
		System.out.println("http://localhost:8080/api/books?id=1 - редактирование книги");
		System.out.println();
		System.out.println("http://localhost:8080/api/authors - список авторов");
		System.out.println("http://localhost:8080/api/genres - список жанров");
		System.out.println("http://localhost:8080/api/comments?id=1 - список комментариев для книги");
	}

}
