package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.ingredient.IngredientCreateDto;
import ru.otus.hw.dto.ingredient.IngredientDto;
import ru.otus.hw.dto.ingredient.IngredientUpdateDto;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.dto.serving.ServingDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.ingredient.IngredientServiceImpl;

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

@DisplayName("Контроллер ингредиентов")
@WebMvcTest(IngredientController.class)
class IngredientControllerTest {
    private static final long FIRST_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IngredientServiceImpl service;

    @DisplayName("Получить все ингредиенты")
    @Test
    void findAll() throws Exception {
        val expect = getExampleList();
        given(service.findAll()).willReturn(expect);
        mvc.perform(get("/api/ingredients"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Получить ингредиент по id")
    @Test
    void findById() throws Exception {
        val dto = getDto();
        given(service.findById(any()))
                .willReturn(dto);

        mvc.perform(get("/api/ingredients/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при получении ингредиента по id")
    @Test
    void findByIdWithException() throws Exception {
        given(service.findById(any()))
                .willThrow(new EntityNotFoundException());

        mvc.perform(get("/api/ingredients/%d".formatted(FIRST_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Получить ингредиенты по receiptId")
    @Test
    void findByReceiptId() throws Exception {
        val expect = getExampleList();
        given(service.findByReceiptId(any()))
                .willReturn(expect);

        mvc.perform(get("/api/receipts/ingredients/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Добавление нового ингредиента")
    @Test
    void create() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new IngredientCreateDto(dto.getReceiptDto().getId(),
                dto.getServingDto().getId(), dto.getQuantity());
        mvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());
    }

    @DisplayName("Ошибка при добавлении нового ингредиента с receipt = null")
    @Test
    void createExceptionWithReceiptNull() throws Exception {
        val dto = getDto();
        val createDto = new IngredientCreateDto(null,
                dto.getServingDto().getId(), dto.getQuantity());
        mvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении нового ингредиента с receipt = null")
    @Test
    void createExceptionWithServingNull() throws Exception {
        val dto = getDto();
        val createDto = new IngredientCreateDto(dto.getReceiptDto().getId(),
                null, dto.getQuantity());
        mvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении нового ингредиента с receipt = null")
    @Test
    void createExceptionWithQuantityNull() throws Exception {
        val dto = getDto();
        val createDto = new IngredientCreateDto(dto.getReceiptDto().getId(),
                dto.getServingDto().getId(), null);
        mvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Изменение ингредиента")
    @Test
    void update() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new IngredientUpdateDto(dto.getId(),
                dto.getReceiptDto().getId(), dto.getServingDto().getId(), dto.getQuantity());
        mvc.perform(patch("/api/ingredients/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при изменении ингредиента с id = null")
    @Test
    void updateExceptionWithIdNull() throws Exception {
        val dto = getDto();
        val updateDto = new IngredientUpdateDto(null,
                dto.getReceiptDto().getId(), dto.getServingDto().getId(), dto.getQuantity());
        mvc.perform(patch("/api/ingredients/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении ингредиента с receipt = null")
    @Test
    void updateExceptionWithReceiptNull() throws Exception {
        val dto = getDto();
        val updateDto = new IngredientUpdateDto(dto.getId(),
                null, dto.getServingDto().getId(), dto.getQuantity());
        mvc.perform(patch("/api/ingredients/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении ингредиента с serving = null")
    @Test
    void updateExceptionWithServingNull() throws Exception {
        val dto = getDto();
        val updateDto = new IngredientUpdateDto(dto.getId(),
                dto.getReceiptDto().getId(), null, dto.getQuantity());
        mvc.perform(patch("/api/ingredients/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении ингредиента с quantity = null")
    @Test
    void updateExceptionWithQuantityNull() throws Exception {
        val dto = getDto();
        val updateDto = new IngredientUpdateDto(dto.getId(),
                dto.getReceiptDto().getId(), dto.getServingDto().getId(), null);
        mvc.perform(patch("/api/ingredients/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Удаление ингредиента")
    @Test
    void deleteEx() throws Exception {
        mvc.perform(delete("/api/ingredients/%d".formatted(getDto().getId())))
                .andExpect(status().isNoContent());
    }

    private List<IngredientDto> getExampleList() {
        return LongStream.range(1L, 4L).boxed()
                .map(this::getDto)
                .toList();
    }

    private IngredientDto getDto() {
        return getDto(FIRST_ID);
    }

    private IngredientDto getDto(Long id) {
        return new IngredientDto(id, getReceiptDto(id),
                        getServingDto(id), new BigDecimal(id));
    }

    private ReceiptDto getReceiptDto(Long id) {
        return new ReceiptDto(id, getFoodDto(id), "receipt_%d".formatted(id));
    }

    private ServingDto getServingDto(Long id) {
        val bd = new BigDecimal(id);
        return new ServingDto(id, "serving_%d".formatted(id), getFoodDto(id),
                bd, bd, bd, bd);
    }

    private FoodDto getFoodDto(Long id) {
        return new FoodDto(id, "food_%d".formatted(id));
    }
}
