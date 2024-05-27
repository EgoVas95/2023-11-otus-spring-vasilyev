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
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptCreateDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptUpdateDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.match.MatchMealtimeAndReceiptServiceImpl;

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

@DisplayName("Контроллер совпадений")
@WebMvcTest(MatchController.class)
class MatchControllerTest {

    private static final long FIRST_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MatchMealtimeAndReceiptServiceImpl service;

    @DisplayName("Получить все совпадения")
    @Test
    void findAll() throws Exception {
        val expect = getExampleList();
        given(service.findAll()).willReturn(expect);
        mvc.perform(get("/api/matches"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Получить совпадениe по id")
    @Test
    void findById() throws Exception {
        val dto = getDto();
        given(service.findById(any()))
                .willReturn(dto);

        mvc.perform(get("/api/matches/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при получении совпадения по id")
    @Test
    void findByIdWithException() throws Exception {
        given(service.findById(any()))
                .willThrow(new EntityNotFoundException());

        mvc.perform(get("/api/matches/%d".formatted(FIRST_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Получить совпадениe по receiptId")
    @Test
    void findByReceiptId() throws Exception {
        val expect = getExampleList();
        given(service.findByReceiptId(any()))
                .willReturn(expect);

        mvc.perform(get("/api/receipts/matches/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Получить совпадениe по mealtimeId")
    @Test
    void findByMealtimeId() throws Exception {
        val expect = getExampleList();
        given(service.findByMealtimeId(any()))
                .willReturn(expect);

        mvc.perform(get("/api/mealtimes/matches/%d".formatted(FIRST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expect)));
    }

    @DisplayName("Добавление нового совпадения")
    @Test
    void create() throws Exception {
        val dto = getDto();
        val createDto = new MatchMealtimeAndReceiptCreateDto(
                dto.getReceiptDto().getId(), dto.getMealtimeTypeDto().getId());

        mvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());
    }

    @DisplayName("Ошибка при добавлении нового совпадения с receipt = null")
    @Test
    void createExceptionWithReceiptNull() throws Exception {
        val dto = getDto();
        val createDto = new MatchMealtimeAndReceiptCreateDto(
                null, dto.getMealtimeTypeDto().getId());

        mvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при добавлении нового совпадения с mealtime = null")
    @Test
    void createExceptionWithMealtimeNull() throws Exception {
        val dto = getDto();
        val createDto = new MatchMealtimeAndReceiptCreateDto(
                dto.getReceiptDto().getId(), null);

        mvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Изменение совпадения")
    @Test
    void update() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new MatchMealtimeAndReceiptUpdateDto(dto.getId(),
                dto.getReceiptDto().getId(), dto.getMealtimeTypeDto().getId());

        mvc.perform(patch("/api/matches/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @DisplayName("Ошибка при изменении совпадения с id = null")
    @Test
    void updateExceptionWithIdNull() throws Exception {
        val dto = getDto();
        val updateDto = new MatchMealtimeAndReceiptUpdateDto(null,
                dto.getReceiptDto().getId(), dto.getMealtimeTypeDto().getId());

        mvc.perform(patch("/api/matches/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении совпадения с receipt = null")
    @Test
    void updateExceptionWithReceiptNull() throws Exception {
        val dto = getDto();
        val updateDto = new MatchMealtimeAndReceiptUpdateDto(dto.getId(),
                null, dto.getMealtimeTypeDto().getId());

        mvc.perform(patch("/api/matches/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Ошибка при изменении совпадения с mealtime = null")
    @Test
    void updateExceptionWithMealtimeNull() throws Exception {
        val dto = getDto();
        val updateDto = new MatchMealtimeAndReceiptUpdateDto(dto.getId(),
                dto.getReceiptDto().getId(), null);

        mvc.perform(patch("/api/matches/%d".formatted(dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists("errorMsgs"));
    }

    @DisplayName("Удаление совпадения")
    @Test
    void deleteEx() throws Exception {
        mvc.perform(delete("/api/matches/%d".formatted(getDto().getId())))
                .andExpect(status().isNoContent());
    }

    private List<MatchMealtimeAndReceiptDto> getExampleList() {
        return LongStream.range(1L, 4L).boxed()
                .map(this::getDto)
                .toList();
    }

    private MatchMealtimeAndReceiptDto getDto() {
        return getDto(FIRST_ID);
    }

    private MatchMealtimeAndReceiptDto getDto(Long id) {
        return new MatchMealtimeAndReceiptDto(id, getReceiptDto(id),
                getMealtimeDto(id));
    }

    private MealtimeTypeDto getMealtimeDto(Long id) {
        return new MealtimeTypeDto(id, "mealtime_%d".formatted(id));
    }

    private ReceiptDto getReceiptDto(Long id) {
        return new ReceiptDto(id, getFoodDto(id), "receipt_%d".formatted(id));
    }

    private FoodDto getFoodDto(Long id) {
        return new FoodDto(id, "food_%d".formatted(id));
    }
}
