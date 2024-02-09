package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Тестирование контроллера книг")
@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final long FIRST_BOOK_ID = 1L;

    private static final long NOT_CONTAIN_BOOK_ID = 1L;
    private static final String NOT_FOUND_ERR = "Book is not found";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @DisplayName("Должен добавить новую книгу")
    @Test
    void shouldAddNewBook() throws Exception {
        given(authorService.findAll()).willReturn(List.of(new Author(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new Genre(1L, "G")));

        BookDto bookDto = new BookDto(null, "a",
                AuthorDto.fromDomainObject(authorService.findAll().get(0)),
                GenreDto.fromDomainObject(genreService.findAll().get(0)));

        mvc.perform(post("/edit_book").flashAttr("book", bookDto))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Должен вернуть правильную книгу")
    @Test
    void shouldGetCorrectBook() throws Exception {
        Book book = new Book(FIRST_BOOK_ID, "B",
                new Author(1L, "A"), new Genre(1L, "G"));

        given(bookService.findById(FIRST_BOOK_ID))
                .willReturn(Optional.of(book));

        mvc.perform(get("/edit_book").param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(status().isOk());
    }

    @DisplayName("Должен вернуться ошибка при поиске книги")
    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(NOT_CONTAIN_BOOK_ID))
                .willReturn(Optional.empty());

        mvc.perform(get("/edit_book").param("id", String.valueOf(NOT_CONTAIN_BOOK_ID)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Должен обновить старую книгу")
    @Test
    void shouldUpdateBook() throws Exception {
        given(authorService.findAll()).willReturn(List.of(new Author(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new Genre(1L, "G")));

        Book book = new Book(FIRST_BOOK_ID, "a",
                authorService.findAll().get(0),
                genreService.findAll().get(0));
        given(bookService.findById(FIRST_BOOK_ID)).willReturn(Optional.of(book));

        mvc.perform(post("/edit_book").flashAttr("book",
                        BookDto.fromDomainObject(book)))
                .andExpect(status().is3xxRedirection());
    }


    @DisplayName("Должен удалить книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/delete").param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(status().is3xxRedirection());
    }
}