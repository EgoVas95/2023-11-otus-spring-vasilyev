package ru.otus.hw.actuator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BookSizeActuator.class,
    properties = { "spring.sql.init.mode=never" })
@EnableAutoConfiguration
@AutoConfigureMockMvc
class ActuatorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void shouldBeOkWhenThereAreBooks() throws Exception {
        when(bookService.findAll()).thenReturn(
                List.of(new BookDto(1L, "Book 1", null, null)));

        mockMvc.perform(get("/actuator"))
                .andExpect(status().isOk());
    }
}
