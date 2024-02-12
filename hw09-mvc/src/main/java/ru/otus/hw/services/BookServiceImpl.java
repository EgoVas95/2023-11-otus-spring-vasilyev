package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(Long id) {
        return BookMapper.fromDomainObject(bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Book with id = %d is not found"
                        .formatted(id))));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream().map(BookMapper::fromDomainObject)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto bookDto) {
        final Long authorId = bookDto.getAuthor().getId();
        final Long genreId = bookDto.getGenre().getId();

        var author = authorRepository.findById(authorId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        var book = BookMapper.toDomainObject(bookDto);
        book.setAuthor(author);
        book.setGenre(genre);
        return BookMapper.fromDomainObject(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto bookDto) {
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
        var book = BookMapper.toDomainObject(bookDto);
        book.setAuthor(author);
        book.setGenre(genre);

        return BookMapper.fromDomainObject(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
