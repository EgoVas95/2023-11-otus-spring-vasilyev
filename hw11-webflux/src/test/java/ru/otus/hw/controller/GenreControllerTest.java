package ru.otus.hw.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.stream.LongStream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование контроллера жанров")
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreServiceImpl genreService;

    @DisplayName("Должен вернуть валидный список жанров")
    @Test
    void shouldGetAllGenres() throws Exception {
        GenreDto[] exampleList = getExampleGenres();

        given(genreService.findAll()).willReturn(Flux.just(exampleList));
        mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(exampleList)));
    }

    private GenreDto[] getExampleGenres() {
        return LongStream.range(1L, 4L).boxed()
                .map(id -> new GenreDto(id, "name %d".formatted(id)))
                .toArray(GenreDto[]::new);
    }
}