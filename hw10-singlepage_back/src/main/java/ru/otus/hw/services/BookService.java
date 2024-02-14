package ru.otus.hw.services;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto findById(Long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto bookDto);

    BookDto update(Long id, BookUpdateDto bookDto);

    void deleteById(Long id);
}
