package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookServiceImpl;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping("/api/books")
    public Flux<BookDto> allBooksList() {
        return bookService.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable(value = "id", required = false) Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PatchMapping("/api/books/{id}")
    public Mono<ResponseEntity<BookDto>> updateBook(@PathVariable("id") Long id,
                              @Valid @RequestBody BookUpdateDto book) {
        book.setId(id);
        return bookService.update(book)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.ACCEPTED))
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping("/api/books")
    public Mono<ResponseEntity<BookDto>> createBook(@Valid @RequestBody BookCreateDto book) {
        return bookService.create(book)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteById(id)
                .then(Mono.fromCallable(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }
}
