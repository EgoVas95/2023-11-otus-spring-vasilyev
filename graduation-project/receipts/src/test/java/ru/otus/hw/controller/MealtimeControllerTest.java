package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.configuration.MongoConfiguration;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Mealtime;
import ru.otus.hw.services.mealtime.MealtimeServiceImpl;

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

@DisplayName("Тестирование контроллера диет")
@EnableAutoConfiguration(exclude = MongoConfiguration.class)
@WebMvcTest(MealtimeController.class)
class MealtimeControllerTest {
    private static final String FIRST_ID = "1";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MealtimeServiceImpl service;

    @DisplayName("get all")
    @Test
    void findAll() throws Exception {
        val exList = getDbEx();
        given(service.findAll()).willReturn(exList);

        mvc.perform(get("/api/mealtimes"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(exList)));
    }

    @DisplayName("find by id")
    @Test
    void findById() throws Exception {
        val expect = getExampleObj();
        given(service.findById(any())).willReturn(expect);
        mvc.perform(get("/api/mealtimes/%s".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("find by id ex")
    @Test
    void findByIdEx() throws Exception {
        given(service.findById(any())).willThrow(EntityNotFoundException.class);

        mvc.perform(get("/api/mealtimes/%s".formatted(FIRST_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("add new")
    @Test
    void create() throws Exception {
        val expect = getExampleObj();
        given(service.create(any()))
                .willReturn(expect);

        val obj = getExampleObj();
        obj.setId(null);

        mvc.perform(post("/api/mealtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(obj)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("add new validate ex")
    @Test
    void createEx() throws Exception {
        val obj = getExampleObj();
        obj.setId(null);
        obj.setName(null);

        mvc.perform(post("/api/mealtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(obj)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("update")
    @Test
    void update() throws Exception {
        val expect = getExampleObj();
        given(service.update(any()))
                .willReturn(expect);

        mvc.perform(patch("/api/mealtimes/%s".formatted(expect.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(expect)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("update ex")
    @Test
    void updateEx() throws Exception {
        val expect = getExampleObj();
        expect.setName(null);

        mvc.perform(patch("/api/mealtimes/%s".formatted(expect.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(expect)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("delete")
    @Test
    void deleteObj() throws Exception {
        mvc.perform(delete("/api/mealtimes/%s".formatted(FIRST_ID)))
                .andExpect(status().isNoContent());
    }

    private List<Mealtime> getDbEx() {
        return LongStream.range(1L, 4L).boxed()
                .map(this::getExampleObj)
                .toList();
    }

    private Mealtime getExampleObj() {
        return getExampleObj(1L);
    }

    private Mealtime getExampleObj(Long id) {
        return new Mealtime(id.toString(), id.toString());
    }
}
