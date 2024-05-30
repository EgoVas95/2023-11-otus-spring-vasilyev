package ru.otus.hw.services.meal;

import ru.otus.hw.dto.meal.MealCreateDto;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.meal.MealUpdateDto;

import java.util.List;

public interface MealService {
    List<MealDto> findAllBySeveralParams(Long mealtimeTypeId,
                                         Long dietTypeId,
                                         Long caloriesTypeId);

    MealDto findById(Long id);

    List<MealDto> findAll();

    MealDto create(MealCreateDto dto);

    MealDto update(MealUpdateDto dto);

    void delete(Long id);
}
