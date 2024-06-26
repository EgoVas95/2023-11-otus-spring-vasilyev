package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.otus.hw.dto.*;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Тестирование контроллера книг")
@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @DisplayName("Должен вернуть валидный список книг")
    @Test
    void shouldGetAllBooks() throws Exception {
        List<BookDto> exampleList = getExampleBookList();

        given(bookService.findAll()).willReturn(exampleList);
        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(exampleList)));
    }

    @DisplayName("Должен вернуть правильную книгу")
    @Test
    void shouldGetCorrectBook() throws Exception {
        BookDto bookDto = getExampleOfBookDto();

        given(bookService.findById(any())).willReturn(bookDto);

        mvc.perform(get("/api/books/%d".formatted(bookDto.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @DisplayName("Должен отработать fallback по получению книги")
    @Test
    void fallbackForGetBook() throws Exception {
        mvc.perform(get("/api/books/%d".formatted(-1L)))
                .andExpect(status().isOk());
    }

    @DisplayName("Должен вернуться ошибка при поиске книги")
    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(any()))
                .willThrow(EntityNotFoundException.class);

        mvc.perform(get("/api/books/%d".formatted(FIRST_BOOK_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Должен добавить новую книгу")
    @Test
    void shouldAddNewBook() throws Exception {
        BookDto bookDto = getExampleOfBookDto();
        given(bookService.create(any()))
                .willReturn(bookDto);

        BookCreateDto bookCreateDto = new BookCreateDto(bookDto.getId(),
                bookDto.getTitle(), bookDto.getAuthor().getId(),
                bookDto.getGenre().getId());

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @DisplayName("Должен выкинуть ошибку при добавлении невалидного автора id")
    @Test
    void shouldExceptionAddNewBookWithInvalidAuthorId() throws Exception {
        BookDto bookDto = getExampleOfBookDto();
        BookCreateDto bookCreateDto = new BookCreateDto(bookDto.getId(),
                bookDto.getTitle(), null, bookDto.getGenre().getId());

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Должен выкинуть ошибку при добавлении невалидного жанра id")
    @Test
    void shouldExceptionAddNewBookWithInvalidGenreId() throws Exception {
        BookDto bookDto = getExampleOfBookDto();
        BookCreateDto bookCreateDto = new BookCreateDto(bookDto.getId(),
                bookDto.getTitle(), bookDto.getAuthor().getId(), null);

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Должен обновить старую книгу")
    @Test
    void shouldUpdateBook() throws Exception {
        BookDto bookDto = getExampleOfBookDto();
        given(bookService.update(any()))
                .willReturn(bookDto);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor().getId(),
                bookDto.getGenre().getId());

        mvc.perform(patch("/api/books/%d".formatted(bookDto.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @DisplayName("Not found exception при попытке обновить книгу")
    @Test
    void notFoundExceptionByUpdate() throws Exception {
        given(bookService.update(any())).willThrow(EntityNotFoundException.class);

        BookDto bookDto = getExampleOfBookDto();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthor().getId(), bookDto.getGenre().getId());
        mvc.perform(patch("/api/books/%d".formatted(FIRST_BOOK_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Должен обновить книгу с невалидным автором")
    @Test
    void nonExceptionByUpdateWithNonValidIdAuthor() throws Exception {
        BookDto book = getExampleOfBookDto();
        given(bookService.findById(book.getId()))
                .willReturn(book);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                null, book.getGenre().getId());

        mvc.perform(patch("/api/books/%d".formatted(book.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isAccepted());
    }

    @DisplayName("Должен обновить книгу с невалидным жанром")
    @Test
    void exceptionByCreateWithNonValidIGenre() throws Exception {
        BookDto book = getExampleOfBookDto();
        given(bookService.findById(book.getId()))
                .willReturn(book);
        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), null);


        mvc.perform(patch("/api/books/%d".formatted(book.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isAccepted());
    }

    @DisplayName("Должен удалить книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(delete("/api/books/%d".formatted(FIRST_BOOK_ID)))
                .andExpect(status().isNoContent());
    }

    private List<BookDto> getExampleBookList() {
        return LongStream.range(1L, 4L).boxed()
                .map(id -> new BookDto(id, "title %d".formatted(id),
                        new AuthorDto(id, "name"), new GenreDto(id, "genre")))
                .toList();
    }

    private BookDto getExampleOfBookDto() {
        given(authorService.findAll()).willReturn(List.of(new AuthorDto(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new GenreDto(1L, "G")));

        return new BookDto(FIRST_BOOK_ID, "a",
                authorService.findAll().get(0),
                genreService.findAll().get(0));
    }
}
