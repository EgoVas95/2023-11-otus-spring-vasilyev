package ru.otus.hw.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.configurations.KeycloakLogoutHandler;
import ru.otus.hw.configurations.SecurityConfig;
import ru.otus.hw.dto.calories.CaloriesTypeDto;
import ru.otus.hw.dto.diets.DietTypeDto;
import ru.otus.hw.dto.meal.MealCreateDto;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.meal.MealUpdateDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.meal.MealServiceImpl;

import java.math.BigDecimal;
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

@DisplayName("Контроллер приёмов пищи")
@WebMvcTest(MealController.class)
@Import({SecurityConfig.class, KeycloakLogoutHandler.class})
class MealControllerTest {

    private static final long FIRST_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MealServiceImpl service;

    @DisplayName("Получить всё по параметрам")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findAllByParams() throws Exception {
        val expect = getExampleList();
        given(service.findAllBySeveralParams(any(), any(), any())).willReturn(expect);
        mvc.perform(get("/api/meals/%d/%d/%d"
                        .formatted(FIRST_ID, FIRST_ID, FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Получить всё")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findAll() throws Exception {
        val expect = getExampleList();
        given(service.findAll()).willReturn(expect);
        mvc.perform(get("/api/meals"))
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

        mvc.perform(get("/api/meals/%d".formatted(FIRST_ID)))
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

        mvc.perform(get("/api/meals/%d".formatted(FIRST_ID)))
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

        val createDto = new MealCreateDto(dto.getMealtimeTypeDto(),
                dto.getDietTypeDto(), dto.getCaloriesDto());
        mvc.perform(post("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }
    @DisplayName("Ошибка при добавлении нового. mealtimeType = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createWithExceptionMealtimeTypeNull() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new MealCreateDto(null,
                dto.getDietTypeDto(), dto.getCaloriesDto());
        mvc.perform(post("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при добавлении нового. dietType = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createWithExceptionDietTypeNull() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new MealCreateDto(dto.getMealtimeTypeDto(),
                null, dto.getCaloriesDto());
        mvc.perform(post("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при добавлении нового. caloriesType = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createWithExceptionCaloriesTypeNull() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new MealCreateDto(dto.getMealtimeTypeDto(),
                dto.getDietTypeDto(), null);
        mvc.perform(post("/api/meals")
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

        val updateDto = new MealUpdateDto(dto.getId(), dto.getMealtimeTypeDto(),
                dto.getDietTypeDto(), dto.getCaloriesDto());
        mvc.perform(patch("/api/meals/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }
    @DisplayName("Ошибка при изменении с id = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithIdNull() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MealUpdateDto(null, dto.getMealtimeTypeDto(),
                dto.getDietTypeDto(), dto.getCaloriesDto());
        mvc.perform(patch("/api/meals/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при изменении с mealtimeType = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithMealTimeTypeNull() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MealUpdateDto(dto.getId(), null,
                dto.getDietTypeDto(), dto.getCaloriesDto());
        mvc.perform(patch("/api/meals/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при изменении с dietType = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithDietTypeNull() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MealUpdateDto(dto.getId(), dto.getMealtimeTypeDto(),
                null, dto.getCaloriesDto());
        mvc.perform(patch("/api/meals/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при изменении с caloriesType = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithCaloriesTypeNull() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MealUpdateDto(dto.getId(), dto.getMealtimeTypeDto(),
                dto.getDietTypeDto(), null);
        mvc.perform(patch("/api/meals/%d".formatted(dto.getId()))
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
        mvc.perform(delete("/api/meals/%d".formatted(FIRST_ID)))
                .andExpect(status().isNoContent());
    }

    private List<MealDto> getExampleList() {
        return LongStream.range(1L, 4L).boxed()
                .map(this::getDto)
                .toList();
    }

    private MealDto getDto() {
        return getDto(FIRST_ID);
    }

    private MealDto getDto(Long id) {
        return new MealDto(id,
                new MealtimeTypeDto(id, "mealtime_%d".formatted(id)),
                new DietTypeDto(id, "diet_%d".formatted(id)),
                new CaloriesTypeDto(id, new BigDecimal(id)));
    }
}
