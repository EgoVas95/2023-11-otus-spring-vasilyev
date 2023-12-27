package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @Mock
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
        given(appProperties.getTestFileName()).willReturn("error.test.questions.csv");
        assertThrows(QuestionReadException.class, dao::findAll);
    }

    @DisplayName("Должен отработать без ошибок")
    @Test
    void shouldNotThrowExceptions() {
        given(appProperties.getTestFileName()).willReturn("test.questions.csv");
        assertThatList(dao.findAll()).isNotNull().isNotEmpty()
                .hasSize(2);
    }
}
