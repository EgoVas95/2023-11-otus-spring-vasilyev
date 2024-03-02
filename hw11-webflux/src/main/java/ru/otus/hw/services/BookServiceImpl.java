package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public Mono<BookDto> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<BookDto> findAll() {
        return bookRepository.findAll()
                .map(bookMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<BookDto> create(BookCreateDto bookDto) {
        final Long authorId = bookDto.getAuthorId();
        final Long genreId = bookDto.getGenreId();

        return authorRepository.findById(authorId)
                .flatMap(author -> genreRepository.findById(genreId)
                        .flatMap(genre -> {
                            var book = bookMapper.toModel(bookDto, author, genre);
                            return bookRepository.save(book)
                                    .map(bookMapper::toDto);
                        }));
    }

    @Transactional
    @Override
    public Mono<BookDto> update(BookUpdateDto bookDto) {
        final Long authorId = bookDto.getAuthorId();
        final Long genreId = bookDto.getGenreId();
        final Long bookId = bookDto.getId();

        return bookRepository.findById(bookId)
                        .flatMap(findedBook -> authorRepository.findById(authorId)
                                .flatMap(author -> genreRepository.findById(genreId)
                                        .flatMap(genre -> {
                                            var book = bookMapper.toModel(bookDto,
                                                    author == null ? findedBook.getAuthor() : author,
                                                    genre == null ? findedBook.getGenre() : genre);
                                            return bookRepository.save(book)
                                                    .map(bookMapper::toDto);
                                        })));
    }

    @Transactional
    @Override
    public Mono<Void> deleteById(Long id) {
        return bookRepository.deleteById(id);
    }
}
