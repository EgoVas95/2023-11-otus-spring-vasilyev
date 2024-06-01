package ru.otus.hw.mealconfigurator.services;

import ru.otus.hw.mealconfigurator.dto.DayDto;
import ru.otus.hw.mealconfigurator.model.MealList;

import java.util.List;

public interface MealListService {
    MealList save(List<DayDto> daysList);

    MealList findForUser();
}
