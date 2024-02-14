package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping("/api/books")
    public List<BookDto> allBooksList() {
        return bookService.findAll();
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable(value = "id", required = false) Long id) {
        return bookService.findById(id);
    }

    @PatchMapping("/api/books/{id}")
    public BookDto updateBook(@PathVariable("id") Long id,
                              @Valid @RequestBody BookUpdateDto book) {
        return bookService.update(id, book);
    }

    @PostMapping("/api/books")
    public BookDto createBook(@Valid @RequestBody BookCreateDto book) {
        return bookService.create(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }
}
