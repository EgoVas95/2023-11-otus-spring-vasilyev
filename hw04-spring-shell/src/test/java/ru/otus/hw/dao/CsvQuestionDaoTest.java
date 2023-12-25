package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест конвертера вопросов из csv файла")
@SpringBootTest
class CsvQuestionDaoTest {

    @MockBean
    private AppProperties appProperties;

    private QuestionDao dao;

    @BeforeEach
    void setUp() {
        dao = new CsvQuestionDao(appProperties);
    }

    @DisplayName("Должен выдать ошибку на пустой InputStream")
    @Test
    void shouldThrowQuestionReadExceptionException() {
        assertThrows(QuestionReadException.class, dao::findAll);
    }

    @DisplayName("Выдаст ошибку из-за неверного формата вопроса")
    @Test
    void shouldThrowUnsupportedQuestionFormatException() {
        given(appProperties.getTestFileNameByLocaleTag()).willReturn("error.test.questions.csv");
        assertThrows(QuestionReadException.class, dao::findAll);
    }

    @DisplayName("Должен отработать без ошибок")
    @Test
    void shouldNotThrowExceptions() {
        given(appProperties.getTestFileNameByLocaleTag()).willReturn("test.questions.csv");
        assertThatList(dao.findAll()).isNotNull().isNotEmpty()
                .hasSize(2);
    }
}