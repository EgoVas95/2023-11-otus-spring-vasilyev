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
import ru.otus.hw.dto.receipt.ReceiptCreateDto;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.dto.receipt.ReceiptUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.receipt.ReceiptServiceImpl;

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

@DisplayName("Контроллер рецептов")
@WebMvcTest(ReceiptController.class)
class ReceiptControllerTest {

    private static final long FIRST_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ReceiptServiceImpl service;

    @DisplayName("Получить все рецепты")
    @Test
    void findAll() throws Exception {
        val expect = getExampleList();
        given(service.findAll()).willReturn(expect);
        mvc.perform(get("/api/receipts"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Получить рецепта по id")
    @Test
    void findById() throws Exception {
        val dto = getDto();
        given(service.findById(any()))
                .willReturn(dto);

        mvc.perform(get("/api/receipts/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при получении рецепта по id")
    @Test
    void findByIdWithException() throws Exception {
        given(service.findById(any()))
                .willThrow(new EntityNotFoundException());

        mvc.perform(get("/api/receipts/%d".formatted(FIRST_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Получить рецепты по foodId")
    @Test
    void findByFoodId() throws Exception {
        val expect = getExampleList();
        given(service.findAllByFoodId(any()))
                .willReturn(expect);

        mvc.perform(get("/api/receipts/food/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Добавление нового рецепта")
    @Test
    void create() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new ReceiptCreateDto(dto.getFoodDto().getId(), dto.getInstruction());
        mvc.perform(post("/api/receipts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());
    }

    @DisplayName("Ошибка при добавлении нового рецепта с foodId = null")
    @Test
    void createExceptionWithFoodIdNull() throws Exception {
        val dto = getDto();
        val createDto = new ReceiptCreateDto(null, dto.getInstruction());
        mvc.perform(post("/api/receipts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении нового рецепта с instruction = null")
    @Test
    void createExceptionWithInstructionNull() throws Exception {
        val dto = getDto();
        val createDto = new ReceiptCreateDto(dto.getFoodDto().getId(), null);
        mvc.perform(post("/api/receipts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Изменение рецепта")
    @Test
    void update() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new ReceiptUpdateDto(dto.getId(),
                dto.getFoodDto().getId(), dto.getInstruction());
        mvc.perform(patch("/api/receipts/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при изменении рецепта с id = null")
    @Test
    void updateExceptionWithIdNull() throws Exception {
        val dto = getDto();
        val updateDto = new ReceiptUpdateDto(null,
                dto.getFoodDto().getId(), dto.getInstruction());
        mvc.perform(patch("/api/receipts/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении рецепта с foodId = null")
    @Test
    void updateExceptionWithFoodIdNull() throws Exception {
        val dto = getDto();
        val updateDto = new ReceiptUpdateDto(dto.getId(),
                null, dto.getInstruction());
        mvc.perform(patch("/api/receipts/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении рецепта с instruction = null")
    @Test
    void updateExceptionWithInstructionNull() throws Exception {
        val dto = getDto();
        val updateDto = new ReceiptUpdateDto(dto.getId(),
                dto.getFoodDto().getId(), null);
        mvc.perform(patch("/api/receipts/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Удаление рецепта")
    @Test
    void deleteEx() throws Exception {
        mvc.perform(delete("/api/receipts/%d".formatted(getDto().getId())))
                .andExpect(status().isNoContent());
    }

    private List<ReceiptDto> getExampleList() {
        return LongStream.range(1L, 4L).boxed()
                .map(this::getDto)
                .toList();
    }

    private ReceiptDto getDto() {
        return getDto(FIRST_ID);
    }

    private ReceiptDto getDto(Long id) {
        return new ReceiptDto(id, getFoodDto(id),
                "receipt_%d".formatted(id));
    }

    private FoodDto getFoodDto(Long id) {
        return new FoodDto(id, "food_%d".formatted(id));
    }
}
