package ru.otus.hw.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "unknownBookListFallback")
    @GetMapping("/api/books")
    public List<BookDto> allBooksList() {
        return bookService.findAll();
    }

    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "unknownBookFallback")
    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable(value = "id", required = false) Long id) {
        return bookService.findById(id);
    }

    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "createUpdateFallback")
    @PatchMapping("/api/books/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public BookDto updateBook(@PathVariable("id") Long id,
                              @Valid @RequestBody BookUpdateDto book) {
        book.setId(id);
        return bookService.update(book);
    }

    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "createUpdateFallback")
    @PostMapping("/api/books")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookCreateDto book) {
        return bookService.create(book);
    }

    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "deleteFallback")
    @DeleteMapping("/api/books/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BookDto unknownBookFallback(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new BookDto(null, "NaN", null, null);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<BookDto> unknownBookListFallback(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ArrayList<>();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String createUpdateFallback(Exception ex) {
        log.error("CREATE/UPDATE EXCEPTION: " + ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void deleteFallback(Exception ex) {
        log.error("DELETE EXCEPTION: " + ex.getMessage(), ex);
    }

}
