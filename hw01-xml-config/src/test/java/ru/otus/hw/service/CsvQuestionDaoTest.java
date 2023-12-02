package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест CsvQuestionDao")
public class CsvQuestionDaoTest {

    @DisplayName("Должен выдать ошибку на пустой InputStream")
    @Test
    void shouldThrowQuestionReadExceptionException() {
        CsvQuestionDao dao = new CsvQuestionDao(
                new AppProperties("empty_test_file_name.csv"));
        assertThrows(QuestionReadException.class, dao::findAll);
    }

    @DisplayName("Должен отработать без ошибок")
    @Test
    void shouldNotThrowExceptions() {
        CsvQuestionDao dao = new CsvQuestionDao(new AppProperties("questions.csv"));
        assertDoesNotThrow(dao::findAll);
    }
}
