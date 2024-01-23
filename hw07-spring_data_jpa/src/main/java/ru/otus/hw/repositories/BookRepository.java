package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Book;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

    @Nonnull
    @EntityGraph("book-entity-graph")
    List<Book> findAll();
}
