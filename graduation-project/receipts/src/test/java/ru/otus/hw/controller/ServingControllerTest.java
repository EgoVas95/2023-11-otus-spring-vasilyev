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
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.serving.ServingCreateDto;
import ru.otus.hw.dto.serving.ServingDto;
import ru.otus.hw.dto.serving.ServingUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.serving.ServingServiceImpl;

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

@DisplayName("Контроллер порций")
@WebMvcTest(ServingController.class)
@Import({SecurityConfig.class, KeycloakLogoutHandler.class})
class ServingControllerTest {
    private static final long FIRST_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ServingServiceImpl service;

    @DisplayName("Получить все порции")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findAll() throws Exception {
        val expect = getExampleList();
        given(service.findAll()).willReturn(expect);
        mvc.perform(get("/api/servings"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Получить порцию по id")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findById() throws Exception {
        val dto = getDto();
        given(service.findById(any()))
                .willReturn(dto);

        mvc.perform(get("/api/servings/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при получении порции по id")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findByIdWithException() throws Exception {
        given(service.findById(any()))
                .willThrow(new EntityNotFoundException());

        mvc.perform(get("/api/servings/%d".formatted(FIRST_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Получить порции с foodId")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findByFoodId() throws Exception {
        val expect = getExampleList();
        given(service.findByFoodId(any()))
                .willReturn(expect);

        mvc.perform(get("/api/servings/food/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Ошибка при получении порции по id")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findByName() throws Exception {
        val expect = getExampleList();
        given(service.findByName(any()))
                .willReturn(expect);

        mvc.perform(get("/api/servings/name/%s".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Получить порции с калориями меньше или равными")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void findByLessOrEqThan() throws Exception {
        val expect = getExampleList();
        given(service.findByCaloriesLessOrEqThan(any()))
                .willReturn(expect);

        mvc.perform(get("/api/servings/calories/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Добавление новой порции")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void create() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());
    }

    @DisplayName("Ошибка при добавлении новой порции с name = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithNameNull() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(null,
                dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с food = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithFoodNull() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                null, dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с calories < 0>")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithCaloriesNegative() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), new BigDecimal(-1),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с calories = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithCaloriesNull() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), null,
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с proteins < 0")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithProteinsNegative() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), dto.getCalories(),
                new BigDecimal(-1), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с proteins = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithProteinsNull() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), dto.getCalories(),
                null, dto.getFats(), dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с fats < 0")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithFatsNegative() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), new BigDecimal(-1), dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с fats = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithFatsNull() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), null, dto.getCarbohydrates());
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с carbohydrates < 0")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithCarbohydratesNegative() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), new BigDecimal(-1));
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении новой порции с carbohydrates = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void createExceptionWithCarbohydratesNull() throws Exception {
        val dto = getDto();
        val createDto = new ServingCreateDto(dto.getName(),
                dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), null);
        mvc.perform(post("/api/servings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Изменение порции")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void update() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при изменении порции с id = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithIdNull() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(null,
                dto.getName(), dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с name = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithNameNull() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                null, dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с food = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithFoodNull() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), null, dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с calories < 0")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithCaloriesNegative() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), new BigDecimal(-1),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с calories = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithCaloriesNull() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), null,
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с proteins < 0")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithProteinsNegative() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), dto.getCalories(),
                new BigDecimal(-1), dto.getFats(), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с proteins = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithProteinsNull() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), dto.getCalories(),
                null, dto.getFats(), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с fats < 0")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithFatsNegative() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), new BigDecimal(-1), dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с fats = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithFatsNull() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), null, dto.getCarbohydrates());
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с carbohydrates < 0")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithCarbohydratesNegative() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), new BigDecimal(-1));
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении порции с carbohydrates = null")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void updateExceptionWithCarbohydratesNull() throws Exception {
        val dto = getDto();
        val updateDto = new ServingUpdateDto(dto.getId(),
                dto.getName(), dto.getFoodDto().getId(), dto.getCalories(),
                dto.getProteins(), dto.getFats(), null);
        mvc.perform(patch("/api/servings/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Удаление порции")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read", "PRODUCT_write"}
    )
    @Test
    void deleteEx() throws Exception {
        mvc.perform(delete("/api/servings/%d".formatted(getDto().getId())))
                .andExpect(status().isNoContent());
    }

    private List<ServingDto> getExampleList() {
        return LongStream.range(1L, 4L).boxed()
                .map(this::getDto)
                .toList();
    }

    private ServingDto getDto() {
        return getDto(FIRST_ID);
    }

    private ServingDto getDto(Long id) {
        val bd = new BigDecimal(id);
        return new ServingDto(id, "serving_%d".formatted(id),
                getFoodDto(id), bd, bd, bd, bd);
    }

    private FoodDto getFoodDto(Long id) {
        return new FoodDto(id, "food_%d".formatted(id));
    }
}
