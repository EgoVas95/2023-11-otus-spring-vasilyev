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
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.meal_positions.MealPositionCreateDto;
import ru.otus.hw.dto.meal_positions.MealPositionDto;
import ru.otus.hw.dto.meal_positions.MealPositionUpdateDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;
import ru.otus.hw.dto.serving.ServingDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.meal_position.MealPositionServiceImpl;

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

@DisplayName("Контроллер позиций приёмов пищи")
@WebMvcTest(MealPositionController.class)
@Import({SecurityConfig.class, KeycloakLogoutHandler.class})
class MealPositionControllerTest {

    private static final long FIRST_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MealPositionServiceImpl service;

    @DisplayName("Получить всё по mealId")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findAllByMealId() throws Exception {
        val expect = getExampleList();
        given(service.findAllByMealId(any())).willReturn(expect);
        mvc.perform(get("/api/meal-positions/meal/%d".formatted(FIRST_ID)))
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
        mvc.perform(get("/api/meal-positions"))
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

        mvc.perform(get("/api/meal-positions/%d".formatted(FIRST_ID)))
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

        mvc.perform(get("/api/meal-positions/%d".formatted(FIRST_ID)))
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

        val createDto = new MealPositionCreateDto(dto.getMeal(),
                dto.getServing(), dto.getQuantity());
        mvc.perform(post("/api/meal-positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }
    @DisplayName("Ошибка при добавлении нового. meal = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createWithExceptionMealNull() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new MealPositionCreateDto(null,
                dto.getServing(), dto.getQuantity());
        mvc.perform(post("/api/meal-positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при добавлении нового. serving = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createWithExceptionServingNull() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new MealPositionCreateDto(dto.getMeal(),
                null, dto.getQuantity());
        mvc.perform(post("/api/meal-positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при добавлении нового. quantity = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createWithExceptionQuantityNull() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new MealPositionCreateDto(dto.getMeal(),
                dto.getServing(), null);
        mvc.perform(post("/api/meal-positions")
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

        val updateDto = new MealPositionUpdateDto(dto.getId(), dto.getMeal(),
                dto.getServing(), dto.getQuantity());
        mvc.perform(patch("/api/meal-positions/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }
    @DisplayName("Ошибка при изменении. id = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithIdNull() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MealPositionUpdateDto(null, dto.getMeal(),
                dto.getServing(), dto.getQuantity());
        mvc.perform(patch("/api/meal-positions/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при изменении. meal = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithMealNull() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MealPositionUpdateDto(dto.getId(), null,
                dto.getServing(), dto.getQuantity());
        mvc.perform(patch("/api/meal-positions/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при изменении. serving = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithServingNull() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MealPositionUpdateDto(dto.getId(), dto.getMeal(),
                null, dto.getQuantity());
        mvc.perform(patch("/api/meal-positions/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }
    @DisplayName("Ошибка при изменении. quantity = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithQuantityNull() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MealPositionUpdateDto(dto.getId(), dto.getMeal(),
                dto.getServing(), null);
        mvc.perform(patch("/api/meal-positions/%d".formatted(dto.getId()))
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
        mvc.perform(delete("/api/meal-positions/%d".formatted(FIRST_ID)))
                .andExpect(status().isNoContent());
    }

    private List<MealPositionDto> getExampleList() {
        return LongStream.range(1L, 4L).boxed()
                .map(this::getDto)
                .toList();
    }

    private MealPositionDto getDto() {
        return getDto(FIRST_ID);
    }

    private MealPositionDto getDto(Long id) {
        return new MealPositionDto(id, getMealDto(id),
                getServingDto(id), new BigDecimal(id));
    }
    
    private MealDto getMealDto(Long id) {
        return new MealDto(id,
                new MealtimeTypeDto(id, "mealtime_%d".formatted(id)),
                new DietTypeDto(id, "diet_%d".formatted(id)),
                new CaloriesTypeDto(id, new BigDecimal(id)));
    }
    
    private ServingDto getServingDto(Long id) {
        return new ServingDto(id, "name_%d".formatted(id),
                new FoodDto(id, "name_%d".formatted(id)),
                new BigDecimal(id), new BigDecimal(id),
                new BigDecimal(id), new BigDecimal(id));
    }
}
