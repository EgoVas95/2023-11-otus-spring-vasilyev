package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.CaloriesType;
import ru.otus.hw.services.calories.CaloriesTypeServiceImpl;

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер типов калоража")
@WebMvcTest(CaloriesTypeController.class)
class CaloriesTypeControllerTest {

    private static final long FIRST_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CaloriesTypeServiceImpl service;


    @DisplayName("Получить всё")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findAll() throws Exception {
        val expect = getExampleList();
        given(service.findAll()).willReturn(expect);
        mvc.perform(get("/api/calories-types"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Получить по id")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findById() throws Exception {
        val dto = getDto();
        given(service.findById(any()))
                .willReturn(dto);

        mvc.perform(get("/api/calories-types/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }
    @DisplayName("Ошибка при получении по id")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findByIdWithException() throws Exception {
        given(service.findById(any()))
                .willThrow(new EntityNotFoundException());

        mvc.perform(get("/api/calories-types/%d".formatted(FIRST_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Добавление нового")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void create() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new CaloriesType(null, dto.getCalories());
        mvc.perform(post("/api/calories-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }
    @DisplayName("Ошибка при добавлении нового")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createWithException() throws Exception {
        val createDto = new CaloriesType(null, null);
        mvc.perform(post("/api/calories-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Изменение")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void update() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new CaloriesType(dto.getId(), 101L);
        mvc.perform(patch("/api/calories-types/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }
    @DisplayName("Ошибка при изменении продукта с id = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithIdNull() throws Exception {
        val dto = getDto();
        val updateDto = new CaloriesType(null, dto.getCalories());
        mvc.perform(patch("/api/calories-types/%s".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при изменении продукта с name = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithNameNull() throws Exception {
        val dto = getDto();
        val updateDto = new CaloriesType(dto.getId(), null);
        mvc.perform(patch("/api/calories-types/%s".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Удаление продукта")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void deleteOk() throws Exception {
        mvc.perform(delete("/api/calories-types/%d".formatted(FIRST_ID)))
                .andExpect(status().isNoContent());
    }

    private List<CaloriesType> getExampleList() {
        return LongStream.range(1L, 4L).boxed()
                .map(this::getDto)
                .toList();
    }

    private CaloriesType getDto() {
        return getDto(FIRST_ID);
    }

    private CaloriesType getDto(Long id) {
        return new CaloriesType(id.toString(), 1L);
    }
}
