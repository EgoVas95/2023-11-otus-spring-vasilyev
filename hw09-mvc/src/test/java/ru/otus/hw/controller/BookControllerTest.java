package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.*;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Test
    void shouldAddNewBook() throws Exception {
        given(authorService.findAll()).willReturn(List.of(new AuthorDto(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new GenreDto(1L, "G")));

        BookCreateDto bookDto = new BookCreateDto(null, "a",
                authorService.findAll().get(0),
                genreService.findAll().get(0));

        mvc.perform(post("/create_book").flashAttr("book", bookDto))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Должен вернуть правильную книгу")
    @Test
    void shouldGetCorrectBook() throws Exception {
        BookDto bookDto = new BookDto(FIRST_BOOK_ID, "B",
                new AuthorDto(1L, "A"), new GenreDto(1L, "G"));

        given(bookService.findById(FIRST_BOOK_ID))
                .willReturn(bookDto);

        mvc.perform(get("/edit_book").param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(status().isOk());
    }

    @DisplayName("Должен вернуться ошибка при поиске книги")
    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(NOT_CONTAIN_BOOK_ID))
                .willThrow(new EntityNotFoundException(null));

        mvc.perform(get("/edit_book").param("id", String.valueOf(NOT_CONTAIN_BOOK_ID)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Должен обновить старую книгу")
    @Test
    void shouldUpdateBook() throws Exception {
        given(authorService.findAll()).willReturn(List.of(new AuthorDto(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new GenreDto(1L, "G")));

        BookDto book = new BookDto(FIRST_BOOK_ID, "a",
                authorService.findAll().get(0),
                genreService.findAll().get(0));
        given(bookService.findById(FIRST_BOOK_ID)).willReturn(book);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor(), book.getGenre());

        mvc.perform(post("/update_book").flashAttr("book", bookUpdateDto))
                .andExpect(status().is3xxRedirection());
    }


    @DisplayName("Должен удалить книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/delete").param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(status().is3xxRedirection());
    }
}