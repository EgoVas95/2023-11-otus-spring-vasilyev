package ru.otus.hw.mealconfigurator.services;

import ru.otus.hw.mealconfigurator.model.Day;

import java.util.List;

public interface DayGenerator {
    List<Day> generate(String dietTypeId, String caloriesTypeId, int dayCount);
}
