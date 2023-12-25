package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест сервиса, проводящего тесты")
@SpringBootTest
class TestServiceImplTest {

    @MockBean
    private QuestionDao dao;
    @MockBean
    private LocalizedIOService ioService;

    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(ioService, dao);
    }

    @DisplayName("Для пустого пользователя не должно быть никаких результатов")
    @Test
    void shouldNotHaveResultsForNullStudent() {
        TestResult testResult = testService.executeTestFor(null);
        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isNull();
        assertThatList(testResult.getAnsweredQuestions()).isEmpty();
        assertThat(testResult.getRightAnswersCount()).isEqualTo(0);
    }


    @DisplayName("Для пустого пользователя не должно быть никаких результатов")
    @Test
    void shouldAskOneQuestion() {
        given(dao.findAll()).willReturn(List.of(new Question("Test question",
                List.of(new Answer("yep", true)))));

        Student student = new Student("Name", "Surname");
        TestResult testResult = testService.executeTestFor(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isNotNull().isEqualTo(student);
        assertThatList(testResult.getAnsweredQuestions()).isNotEmpty();
        assertThat(testResult.getAnsweredQuestions().size()).isEqualTo(1);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(1);
    }
}