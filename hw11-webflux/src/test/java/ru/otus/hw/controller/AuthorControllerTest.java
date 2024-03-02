package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorServiceImpl;

import java.util.stream.LongStream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование контроллера авторов")
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorServiceImpl authorService;

    @DisplayName("Должен вернуть валидный список авторов")
    @Test
    void shouldGetAllAuthors() throws Exception {
        AuthorDto[] exampleList = getExampleAuthors();
        given(authorService.findAll()).willReturn(Flux.just(exampleList));


        mvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(exampleList)));
    }

    private AuthorDto[] getExampleAuthors() {
        return LongStream.range(1L, 4L).boxed()
                .map(id -> new AuthorDto(id, "name %d".formatted(id)))
                .toArray(AuthorDto[]::new);
    }
}