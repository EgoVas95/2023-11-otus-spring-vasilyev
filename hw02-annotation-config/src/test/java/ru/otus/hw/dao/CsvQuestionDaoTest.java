package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

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

    @DisplayName("Должен отработать без ошибок")
    @Test
    void shouldNotThrowExceptions() {
        given(appProperties.getTestFileName()).willReturn("questions.csv");
        assertDoesNotThrow(dao::findAll);
    }
}