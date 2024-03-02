package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Book;


public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    @Nonnull
    @Override
    Flux<Book> findAll();

    @Nonnull
    @Override
    Mono<Book> findById(@Nonnull Long id);
}
