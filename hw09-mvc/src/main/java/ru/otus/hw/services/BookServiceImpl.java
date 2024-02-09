package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public Book create(BookDto bookDto) {
        final Long authorId = bookDto.getAuthor().getId();
        final Long genreId = bookDto.getGenre().getId();

        var author = authorRepository.findById(authorId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        var book = new Book(null, bookDto.getTitle(), author, genre);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book update(BookDto bookDto) {
        final Long id = bookDto.getId();
        final Long authorId = bookDto.getAuthor().getId();
        final Long genreId = bookDto.getGenre().getId();

        bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Book with id %d not found".formatted(id)));

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Genre with id %d not found".formatted(genreId)));
        var book = new Book(id, bookDto.getTitle(), author, genre);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
