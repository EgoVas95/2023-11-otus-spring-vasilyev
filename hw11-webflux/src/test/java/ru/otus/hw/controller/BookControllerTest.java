package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@DisplayName("Тестирование контроллера книг")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    private static final String FIRST_BOOK_ID = "1";

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookMapper mapper;

    @DisplayName("Должен вернуть валидный список книг")
    @Test
    void shouldGetAllBooks() {
        Book[] exampleList = getExampleBooks();
        given(bookRepository.findAll()).willReturn(Flux.just(exampleList));
        var result = webTestClient.get().uri("/api/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();
        var step = StepVerifier.create(result);
        StepVerifier.Step<BookDto> stepResult = null;
        for (Book book : exampleList) {
            stepResult = step.expectNext(mapper.toDto(book));
        }

        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @DisplayName("Должен вернуть правильную книгу")
    @Test
    void shouldGetCorrectBook() {
        Book book = getExampleOfBook();
        given(bookRepository.findById((String) any())).willReturn(Mono.just(book));

        var result = webTestClient.get().uri("/api/books/%s".formatted(book.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();

        var step = StepVerifier.create(result);
        StepVerifier.Step<BookDto> stepResult = step.expectNext(mapper.toDto(book));
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @DisplayName("Должен вернуться ошибка при поиске книги")
    @Test
    void shouldGetNotFoundEntity() {
        given(bookRepository.findById((String) any()))
                .willThrow(EntityNotFoundException.class);

        webTestClient.get()
                .uri("/api/books/".concat(FIRST_BOOK_ID))
                .exchange()
                .expectStatus().isNotFound();
    }

    @DisplayName("Должен добавить новую книгу")
    @Test
    void shouldAddNewBook() {
        Book book = getExampleOfBook();

        given(authorRepository.findById((String) any()))
                .willReturn(Mono.just(book.getAuthor()));
        given(genreRepository.findById((String) any()))
                .willReturn(Mono.just(book.getGenre()));
        given(bookRepository.save(any()))
                .willReturn(Mono.just(book));

        BookCreateDto bookCreateDto = new BookCreateDto(book.getId(),
                book.getTitle(), book.getAuthor().getId(),
                book.getGenre().getId());

        var result = webTestClient
                .post().uri("/api/books")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreateDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(BookDto.class)
                .getResponseBody();

        var step = StepVerifier.create(result);
        StepVerifier.Step<BookDto> stepResult = step.expectNext(mapper.toDto(book));
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @DisplayName("Должен выкинуть ошибку при добавлении невалидного автора id")
    @Test
    void shouldExceptionAddNewBookWithInvalidAuthorId() {
        Book book = getExampleOfBook();
        given(authorRepository.findById((String) any()))
                .willReturn(Mono.just(book.getAuthor()));
        given(genreRepository.findById((String) any()))
                .willReturn(Mono.just(book.getGenre()));
        given(bookRepository.save(any()))
                .willReturn(Mono.just(book));

        BookCreateDto bookCreateDto = new BookCreateDto(book.getId(),
                book.getTitle(), null, book.getGenre().getId());

        webTestClient.post()
                .uri("/api/books")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreateDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @DisplayName("Должен выкинуть ошибку при добавлении невалидного жанра id")
    @Test
    void shouldExceptionAddNewBookWithInvalidGenreId() {
        Book book = getExampleOfBook();
        BookCreateDto bookCreateDto = new BookCreateDto(book.getId(),
                book.getTitle(), book.getAuthor().getId(), null);

        webTestClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreateDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @DisplayName("Должен обновить старую книгу")
    @Test
    void shouldUpdateBook() {
        Book book = getExampleOfBook();

        given(authorRepository.findById((String) any()))
                .willReturn(Mono.just(book.getAuthor()));
        given(genreRepository.findById((String) any()))
                .willReturn(Mono.just(book.getGenre()));
        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(book));
        given(bookRepository.save(any()))
                .willReturn(Mono.just(book));

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenre().getId());

        var result = webTestClient
                .patch().uri("/api/books/".concat(book.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookUpdateDto)
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(BookDto.class)
                .getResponseBody();

        var step = StepVerifier.create(result);
        StepVerifier.Step<BookDto> stepResult = step.expectNext(mapper.toDto(book));
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @DisplayName("Not found exception при попытке обновить книгу")
    @Test
    void notFoundExceptionByUpdate() {
        Book book = getExampleOfBook();

        given(authorRepository.findById((String) any()))
                .willReturn(Mono.just(book.getAuthor()));
        given(genreRepository.findById((String) any()))
                .willReturn(Mono.just(book.getGenre()));
        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(book));
        given(bookRepository.save(any())).willThrow(EntityNotFoundException.class);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), book.getGenre().getId());

        webTestClient.patch()
                .uri("/api/books/".concat(FIRST_BOOK_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookUpdateDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @DisplayName("Должен удалить книгу")
    @Test
    void shouldDeleteBook() {
        given(bookRepository.deleteById((String) any())).willReturn(Mono.empty());

        var result = webTestClient
                .delete().uri("/api/books/".concat(FIRST_BOOK_ID))
                .exchange()
                .expectStatus().isNoContent()
                .returnResult(Void.class)
                .getResponseBody();

        var step = StepVerifier.create(result);
        step.verifyComplete();
    }

    private Book[] getExampleBooks() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(String.valueOf(id), "title %d".formatted(id),
                        new Author(String.valueOf(id), "name"),
                        new Genre(String.valueOf(id), "genre")))
                .toArray(Book[]::new);
    }

    private Book getExampleOfBook() {
        given(authorRepository.findAll()).willReturn(Flux.just(new Author("1", "A")));
        given(genreRepository.findAll()).willReturn(Flux.just(new Genre("1", "G")));

        Author author = authorRepository.findAll().blockFirst();
        assertThat(author).isNotNull();

        Genre genre = genreRepository.findAll().blockFirst();
        assertThat(genre).isNotNull();

        return new Book(FIRST_BOOK_ID, "a", author, genre);
    }
}