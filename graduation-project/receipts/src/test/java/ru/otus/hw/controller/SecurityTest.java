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
import ru.otus.hw.dto.food.FoodCreateDto;
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.food.FoodUpdateDto;
import ru.otus.hw.services.food.FoodServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест SecurityConfig")
@WebMvcTest(FoodController.class)
@Import({SecurityConfig.class, KeycloakLogoutHandler.class})
public class SecurityTest {

    private static final String API_EXAMPLE_URL = "/api/example";

    private static final String API_FOOD_URL = "/api/foods";

    private static final long FIRST_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FoodServiceImpl service;

    @DisplayName("Должен вернуть редирект на страницу login")
    @Test
    void shouldReturnRedirectToLoginPage() throws Exception {
        mvc.perform(get(API_EXAMPLE_URL)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("GET. Валидный запрос")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read"}
    )
    @Test
    void getOk() throws Exception {
        mvc.perform(get(API_FOOD_URL))
                .andExpect(status().isOk());
    }
    @DisplayName("GET. Вложенный запрос. Валидный запрос")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read"}
    )
    @Test
    void getWithMultiple() throws Exception {
        val dto = getDto();
        given(service.findById(any()))
                .willReturn(dto);

        mvc.perform(get("%s/%d".formatted(API_FOOD_URL, FIRST_ID)))
                .andExpect(status().isOk());
    }
    @DisplayName("GET. Ошибка no authorized")
    @Test
    void getNotAuthEx() throws Exception {
        mvc.perform(get(API_EXAMPLE_URL))
                .andExpect(status().isUnauthorized());
    }
    @DisplayName("GET. Вложенный запрос. Ошибка no authorized")
    @Test
    void getAuthorityEx() throws Exception {
        mvc.perform(get(API_EXAMPLE_URL))
                .andExpect(status().isUnauthorized());
    }
    @DisplayName("GET. Ошибка по неправильному authority")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_write"}
    )
    @Test
    void getWithMultipleAllNotAuthEx() throws Exception {
        val dto = getDto();
        given(service.findById(any()))
                .willReturn(dto);

        mvc.perform(get("%s/%d".formatted(API_EXAMPLE_URL, FIRST_ID)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("POST. Валидный запрос")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_write"}
    )
    @Test
    void postOk() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new FoodCreateDto(dto.getId(), dto.getName());
        mvc.perform(post(API_FOOD_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());
    }
    @DisplayName("POST. Ошибка no authorized")
    @Test
    void postNotAuthEx() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new FoodCreateDto(dto.getId(), dto.getName());
        mvc.perform(post(API_FOOD_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isUnauthorized());
    }
    @DisplayName("POST. Ошибка по неправильному authority")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read"}
    )
    @Test
    void postAuthorityEx() throws Exception {
        val dto = getDto();
        given(service.create(any()))
                .willReturn(dto);

        val createDto = new FoodCreateDto(dto.getId(), dto.getName());
        mvc.perform(post(API_FOOD_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("PATCH. Валидный запрос")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_write"}
    )
    @Test
    void patchOk() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new FoodUpdateDto(dto.getId(), dto.getName());
        mvc.perform(patch("%s/%d".formatted(API_FOOD_URL, dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }
    @DisplayName("PATCH. Ошибка no authorized")
    @Test
    void updateNotAuthEx() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new FoodUpdateDto(dto.getId(), dto.getName());
        mvc.perform(patch("%s/%d".formatted(API_FOOD_URL, dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isUnauthorized());
    }
    @DisplayName("PATCH. Ошибка по неправильному authority")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read"}
    )
    @Test
    void updateAuthorityEx() throws Exception {
        val dto = getDto();
        given(service.update(any()))
                .willReturn(dto);

        val updateDto = new FoodUpdateDto(dto.getId(), dto.getName());
        mvc.perform(patch("%s/%d".formatted(API_FOOD_URL, dto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("DELETE. Валидный запрос")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_write"}
    )
    @Test
    void deleteOk() throws Exception {
        mvc.perform(delete("%s/%d".formatted(API_FOOD_URL, FIRST_ID)))
                .andExpect(status().isNoContent());
    }
    @DisplayName("DELETE. Ошибка no authorized")
    @Test
    void deleteNotAuthEx() throws Exception {
        mvc.perform(delete("%s/%d".formatted(API_FOOD_URL, FIRST_ID)))
                .andExpect(status().isUnauthorized());
    }
    @DisplayName("DELETE. Ошибка по неправильному authority")
    @WithMockUser(
            username = "user",
            authorities = {"PRODUCT_read"}
    )
    @Test
    void deleteAuthorityEx() throws Exception {
        mvc.perform(delete("%s/%d".formatted(API_FOOD_URL, FIRST_ID)))
                .andExpect(status().isForbidden());
    }

    private FoodDto getDto() {
        return new FoodDto(FIRST_ID, "food %d".formatted(FIRST_ID));
    }
}
