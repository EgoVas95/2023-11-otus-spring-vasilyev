package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование контроллера книг")
@WebMvcTest(CommentController.class)
class CommentControllerTest {
    private static final Long FIRST_BOOK_ID = 1L;
    private static final Long FIRST_COMMENT_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentServiceImpl commentService;

    @DisplayName("Должен вернуть валидный список комментариев")
    @Test
    void shouldGetAllBooks() throws Exception {
        List<CommentDto> exampleList = getExampleCommentList();
        given(commentService.findAllForBook(any())).willReturn(exampleList);

        mvc.perform(get("/api/books/%d/comments".formatted(FIRST_BOOK_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(exampleList)));
    }

    @DisplayName("Должна вернуться ошибка при получении всех комментариев для книги")
    @Test
    void shouldGetNotFoundBook() throws Exception {
        given(commentService.findAllForBook(any())).willThrow(EntityNotFoundException.class);

        mvc.perform(get("/api/books/%d/comments".formatted(FIRST_BOOK_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Должен вернуть правильный комментарий")
    @Test
    void shouldGetCorrectComment() throws Exception {
        CommentDto dto = getExampleOfCommentDto();

        given(commentService.findById(any())).willReturn(dto);

        mvc.perform(get("/api/books/%d/comments/%d".formatted(FIRST_BOOK_ID, FIRST_COMMENT_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Должна вернуться ошибка при поиске комментария")
    @Test
    void shouldGetNotFoundComment() throws Exception {
        given(commentService.findById(any())).willThrow(EntityNotFoundException.class);

        mvc.perform(get("/api/books/%d/comments/%d".formatted(FIRST_BOOK_ID, FIRST_COMMENT_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Должен добавить новый комментарий")
    @Test
    void shouldAddNewComment() throws Exception {
        CommentDto dto = getExampleOfCommentDto();
        given(commentService.create(any()))
                .willReturn(dto);

        CommentCreateDto createDto = new CommentCreateDto(dto.getText(),
                dto.getBookId());

        mvc.perform(post("/api/books/%d/comments".formatted(FIRST_BOOK_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при добавлении с невалидным text")
    @Test
    void shouldThrowExAddInvalidText() throws Exception {
        CommentDto dto = getExampleOfCommentDto();
        CommentCreateDto createDto = new CommentCreateDto(null,
                dto.getBookId());

        mvc.perform(post("/api/books/%d/comments".formatted(FIRST_BOOK_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Ошибка при добавлении с невалидным book_id")
    @Test
    void shouldThrowExAddInvalidBookId() throws Exception {
        CommentDto dto = getExampleOfCommentDto();
        given(commentService.create(any()))
                .willReturn(dto);

        CommentCreateDto createDto = new CommentCreateDto("123",
                null);

        mvc.perform(post("/api/books/%d/comments".formatted(FIRST_BOOK_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Должен обновить старый комментарий")
    @Test
    void shouldUpdateBook() throws Exception {
        CommentDto dto = getExampleOfCommentDto();
        given(commentService.update(any(), any()))
                .willReturn(dto);

        CommentUpdateDto updateDto = new CommentUpdateDto(dto.getText(), dto.getBookId());

        mvc.perform(patch("/api/books/%d/comments/%d".formatted(FIRST_BOOK_ID, FIRST_COMMENT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при изменении с невалидным text")
    @Test
    void shouldThrowExUpdInvalidText() throws Exception {
        CommentDto dto = getExampleOfCommentDto();
        CommentUpdateDto updateDto = new CommentUpdateDto(null, dto.getBookId());

        mvc.perform(patch("/api/books/%d/comments/%d".formatted(FIRST_BOOK_ID, FIRST_COMMENT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Ошибка при изменении с невалидным book_id")
    @Test
    void shouldThrowExUpdInvalidBookId() throws Exception {
        CommentDto dto = getExampleOfCommentDto();
        CommentUpdateDto updateDto = new CommentUpdateDto(dto.getText(), null);

        mvc.perform(patch("/api/books/%d/comments/%d".formatted(FIRST_BOOK_ID, FIRST_COMMENT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Not found exception при попытке обновить коммент")
    @Test
    void notFoundExceptionByUpdate() throws Exception {
        given(commentService.update(any(), any()))
                .willThrow(EntityNotFoundException.class);

        CommentDto dto = getExampleOfCommentDto();
        CommentUpdateDto updateDto = new CommentUpdateDto(dto.getText(), dto.getBookId());

        mvc.perform(patch("/api/books/%d/comments/%d".formatted(FIRST_BOOK_ID, FIRST_COMMENT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Должен удалить коммент")
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(delete("/api/books/%d/comments/%d".formatted(FIRST_BOOK_ID, FIRST_COMMENT_ID)))
                .andExpect(status().isOk());
    }

    private List<CommentDto> getExampleCommentList() {
        return LongStream.range(1L, 4L).boxed()
                .map(id -> new CommentDto(id, "text %d".formatted(id), id))
                .toList();
    }

    private CommentDto getExampleOfCommentDto() {
        return new CommentDto(FIRST_COMMENT_ID, "a", FIRST_COMMENT_ID);
    }
}