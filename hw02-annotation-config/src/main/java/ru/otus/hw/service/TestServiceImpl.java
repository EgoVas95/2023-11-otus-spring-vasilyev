package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var maxQuestionSize = question.answers().size();

            var answerIdx = ioService.readIntForRangeWithPrompt(0, maxQuestionSize,
                    QuestionStringFormatter.formatQuestion(question),
                    "Допускается только числовой ввод от 1 до " + maxQuestionSize);
            Answer answer = question.answers().get(answerIdx);

            var isAnswerValid = answer.isCorrect(); // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
