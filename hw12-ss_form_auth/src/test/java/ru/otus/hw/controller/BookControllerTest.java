package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Тестирование контроллера книг")
@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final long FIRST_BOOK_ID = 1L;

    private static final long NOT_CONTAIN_BOOK_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @DisplayName("Должен добавить новую книгу")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldAddNewBook() throws Exception {
        BookDto book = getExampleOfBookDto();
        BookCreateDto bookCreateDto = new BookCreateDto(null, book.getTitle(), book.getAuthor().getId(),
                book.getGenre().getId());

        mvc.perform(post("/create_book")
                        .with(csrf())
                        .flashAttr("book", bookCreateDto))
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("Должен вернуть ошибку аутентификации для create_book")
    @Test
    void shouldReturn401ForCreateBook() throws Exception {
        mvc.perform(get("/create_book"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Должен вернуть правильную книгу")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldGetCorrectBook() throws Exception {
        BookDto bookDto = new BookDto(FIRST_BOOK_ID, "B",
                new AuthorDto(1L, "A"), new GenreDto(1L, "G"));

        given(bookService.findById(FIRST_BOOK_ID))
                .willReturn(bookDto);

        mvc.perform(get("/edit_book").param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(status().isOk());
    }

    @DisplayName("Должен вернуть ошибку аутентификации для edit_book")
    @Test
    void shouldReturn401ForEditeBook() throws Exception {
        mvc.perform(get("/edit_book"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Должен вернуться ошибка при поиске книги")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(NOT_CONTAIN_BOOK_ID))
                .willThrow(new EntityNotFoundException(null));

        mvc.perform(get("/edit_book").param("id", String.valueOf(NOT_CONTAIN_BOOK_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Должен обновить старую книгу")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldUpdateBook() throws Exception {
        BookDto book = getExampleOfBookDto();
        given(bookService.findById(book.getId())).willReturn(book);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), book.getGenre().getId());

        mvc.perform(post("/update_book").with(csrf()).flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("Должен вернуть ошибку аутентификации для update_book")
    @Test
    void shouldReturn401ForUpdateBook() throws Exception {
        mvc.perform(get("/update_book"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Not found exception при попытке обновить книгу")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void notFoundExceptionByUpdate() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto(null, null,
                null, null);

        mvc.perform(post("/update_book").with(csrf()).flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/edit_book?id=%d".formatted(null)));
    }

    @DisplayName("Ошибка валидации id автора при создании книги")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void exceptionByCreateWithNonValidIdAuthor() throws Exception {
        BookDto book = getExampleOfBookDto();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                null, book.getGenre().getId());

        mvc.perform(post("/update_book").with(csrf()).flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/edit_book?id=%d".formatted(book.getId())));
    }

    @DisplayName("Ошибка валидации id жанра при создании книги")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void exceptionByCreateWithNonValidIGenre() throws Exception {
        BookDto book = getExampleOfBookDto();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), null);

        mvc.perform(post("/update_book").with(csrf()).flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/edit_book?id=%d".formatted(book.getId())));
    }

    @DisplayName("Должен удалить книгу")
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/delete").with(csrf()).param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(redirectedUrl("/"));
    }


    @DisplayName("Должен вернуть ошибку аутентификации для delete_book")
    @Test
    void shouldReturn401ForDeleteBook() throws Exception {
        mvc.perform(get("/delete"))
                .andExpect(status().isUnauthorized());
    }

    private BookDto getExampleOfBookDto() {
        given(authorService.findAll()).willReturn(List.of(new AuthorDto(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new GenreDto(1L, "G")));

        return new BookDto(FIRST_BOOK_ID, "a",
                authorService.findAll().get(0),
                genreService.findAll().get(0));
    }
}