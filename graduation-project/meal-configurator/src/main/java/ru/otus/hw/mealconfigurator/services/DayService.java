package ru.otus.hw.mealconfigurator.services;

import ru.otus.hw.mealconfigurator.model.Day;

import java.time.LocalDate;
import java.util.List;

public interface DayService {
    Day findById(String id);

    List<Day> findAllByUserId(String userId);

    Day findFirstByUserIdAndDate(String userId, LocalDate date);

    void deleteAllByUserId(String userId);

    Day create(Day day);

    Day update(Day day);

    void delete(String id);
}
