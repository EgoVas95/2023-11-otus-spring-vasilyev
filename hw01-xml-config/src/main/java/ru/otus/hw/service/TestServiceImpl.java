package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;


@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;
    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        if (ioService != null && questionDao != null) {
            for (Question question : questionDao.findAll()) {
                if (question != null && question.text() != null) {
                    ioService.printLine(QuestionStringFormatter.formatQuestion(question));
                }
            }
        }
    }
}
