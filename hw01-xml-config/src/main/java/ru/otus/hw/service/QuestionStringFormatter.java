package ru.otus.hw.service;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

public final class QuestionStringFormatter {
    public static String formatQuestion(Question question) {
        String res = null;
        if (question != null && question.text() != null) {
            res = String.format("%s: \n", question.text());
            if (question.answers() != null) {
                int num = 1;
                for (Answer answer : question.answers()) {
                    if (answer != null && answer.text() != null) {
                        res = res.concat(String.format("%d) %s\t", num++, answer.text()));
                    }
                }
            }
        }
        return res;
    }
}
