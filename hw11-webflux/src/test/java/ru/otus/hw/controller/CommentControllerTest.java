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
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.CommentMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Тестирование контроллера книг")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerTest {
    private static final String FIRST_BOOK_ID = "1";
    private static final String FIRST_COMMENT_ID = "1";

    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CommentMapper mapper;

    @DisplayName("Должен вернуть валидный список комментариев")
    @Test
    void shouldGetAllBooks() {
        Comment[] exampleList = getExampleComments();

        given(commentRepository.findAllByBookId(any())).willReturn(Flux.just(exampleList));

        var result = webTestClient.get()
                .uri("/api/books/%s/comments".formatted(FIRST_BOOK_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(CommentDto.class)
                .getResponseBody();
        var step = StepVerifier.create(result);
        StepVerifier.Step<CommentDto> stepResult = null;
        for (Comment comment : exampleList) {
            stepResult = step.expectNext(mapper.toDto(comment));
        }

        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @DisplayName("Должна вернуться ошибка при получении всех комментариев для книги")
    @Test
    void shouldGetNotFoundBook() {
        given(commentRepository.findAllByBookId(any())).willThrow(EntityNotFoundException.class);

        webTestClient.get()
                .uri("/api/books/%s/comments".formatted(FIRST_BOOK_ID))
                .exchange()
                .expectStatus().isNotFound();
    }

    @DisplayName("Должен вернуть правильный комментарий")
    @Test
    void shouldGetCorrectComment() {
        Comment comment = getExampleOfCommentDto();

        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(comment.getBook()));
        given(commentRepository.findById((String) any()))
                .willReturn(Mono.just(comment));

        var result = webTestClient.get()
                .uri("/api/comments/%s".formatted(FIRST_COMMENT_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(CommentDto.class)
                .getResponseBody();

        var step = StepVerifier.create(result);
        StepVerifier.Step<CommentDto> stepResult = step.expectNext(mapper.toDto(comment));
        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @DisplayName("Должна вернуться ошибка при поиске комментария")
    @Test
    void shouldGetNotFoundComment() {
        given(commentRepository.findById((String) any())).willThrow(EntityNotFoundException.class);

        webTestClient.get()
                .uri("/api/comments/%s".formatted(FIRST_COMMENT_ID))
                .exchange()
                .expectStatus().isNotFound();
    }

    @DisplayName("Должен добавить новый комментарий")
    @Test
    void shouldAddNewComment() {
        Comment comment = getExampleOfCommentDto();

        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(comment.getBook()));
        given(commentRepository.save(any()))
                .willReturn(Mono.just(comment));

        CommentCreateDto createDto = new CommentCreateDto(comment.getText(),
                comment.getBook().getId());

        var result = webTestClient.post()
                .uri("/api/comments")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(CommentDto.class)
                .getResponseBody();

        var step = StepVerifier.create(result);
        StepVerifier.Step<CommentDto> stepResult = step.expectNext(mapper.toDto(comment));
        stepResult.verifyComplete();
    }

    @DisplayName("Ошибка при добавлении с невалидным text")
    @Test
    void shouldThrowExAddInvalidText() throws Exception {
        Comment comment = getExampleOfCommentDto();
        CommentCreateDto createDto = new CommentCreateDto(null,
                comment.getBook().getId());

        webTestClient.post()
                .uri("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @DisplayName("Ошибка при добавлении с невалидным book_id")
    @Test
    void shouldThrowExAddInvalidBookId() {
        Comment comment = getExampleOfCommentDto();
        given(commentRepository.save(any()))
                .willReturn(Mono.just(comment));

        CommentCreateDto createDto = new CommentCreateDto("123",
                null);

        webTestClient.post()
                .uri("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @DisplayName("Должен обновить старый комментарий")
    @Test
    void shouldUpdateBook() {
        Comment comment = getExampleOfCommentDto();

        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(comment.getBook()));
        given(commentRepository.findById((String) any()))
                .willReturn(Mono.just(comment));
        given(commentRepository.save(any())).willReturn(Mono.just(comment));

        CommentUpdateDto updateDto = new CommentUpdateDto(FIRST_COMMENT_ID,
                comment.getText(), comment.getBook().getId());

        var result = webTestClient.patch()
                .uri("/api/comments/".concat(FIRST_COMMENT_ID))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateDto)
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(CommentDto.class)
                .getResponseBody();

        var step = StepVerifier.create(result);
        StepVerifier.Step<CommentDto> stepResult = step.expectNext(mapper.toDto(comment));

        assertThat(stepResult).isNotNull();
        stepResult.verifyComplete();
    }

    @DisplayName("Ошибка при изменении с невалидным text")
    @Test
    void shouldThrowExUpdInvalidText() {
        Comment comment = getExampleOfCommentDto();
        CommentUpdateDto updateDto = new CommentUpdateDto(null, comment.getText(), FIRST_BOOK_ID);

        webTestClient.patch()
                .uri("/api/comments/%s".formatted(FIRST_COMMENT_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @DisplayName("Ошибка при изменении с невалидным book_id")
    @Test
    void shouldThrowExUpdInvalidBookId() {
        Comment comment = getExampleOfCommentDto();

        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(comment.getBook()));
        given(commentRepository.findById((String) any()))
                .willReturn(Mono.just(comment));
        given(commentRepository.save(any())).willThrow(EntityNotFoundException.class);

        CommentUpdateDto updateDto = new CommentUpdateDto(
                comment.getId(), comment.getText(), null);

        webTestClient.patch()
                .uri("/api/comments/%s".formatted(FIRST_COMMENT_ID))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @DisplayName("Not found exception при попытке обновить коммент")
    @Test
    void notFoundExceptionByUpdate() {
        Comment comment = getExampleOfCommentDto();

        given(bookRepository.findById((String) any()))
                .willReturn(Mono.just(comment.getBook()));
        given(commentRepository.findById((String) any()))
                .willReturn(Mono.just(comment));
        given(commentRepository.save(any())).willThrow(EntityNotFoundException.class);

        CommentUpdateDto updateDto = new CommentUpdateDto(comment.getId(), comment.getText(),
                comment.getBook().getId());

        webTestClient.patch()
                .uri("/api/comments/%s".formatted(FIRST_COMMENT_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @DisplayName("Должен удалить коммент")
    @Test
    void shouldDeleteBook() {
        given(commentRepository.deleteById((String) any())).willReturn(Mono.empty());

        var result = webTestClient
                .delete().uri("/api/comments/%s".formatted(FIRST_COMMENT_ID))
                .exchange()
                .expectStatus().isNoContent()
                .returnResult(Void.class)
                .getResponseBody();

        var step = StepVerifier.create(result);
        step.verifyComplete();
    }

    private Comment[] getExampleComments() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Comment(String.valueOf(id), "text %d".formatted(id),
                        new Book(String.valueOf(id), "title %d".formatted(id),
                                new Author("fullname %d".formatted(id)),
                                new Genre("name %d".formatted(id)))))
                .toArray(Comment[]::new);
    }

    private Comment getExampleOfCommentDto() {
        return new Comment(FIRST_COMMENT_ID, "c",
                new Book(FIRST_BOOK_ID, "b",
                        new Author("a"),
                        new Genre("g")));
    }

}