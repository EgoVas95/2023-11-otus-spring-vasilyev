package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("http://localhost:8080 - список книг");
		System.out.println("http://localhost:8080/edit_book - создание книги");
		System.out.println("http://localhost:8080/edit_book?id=1 - редактирование книги");
		System.out.println();
		System.out.println("http://localhost:8080/authors - список авторов");
		System.out.println("http://localhost:8080/genres - список жанров");
		System.out.println("http://localhost:8080/book_comments?id=1 - список комментариев для книги");
	}

}
