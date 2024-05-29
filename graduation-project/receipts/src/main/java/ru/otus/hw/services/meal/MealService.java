package ru.otus.hw.services.meal;

import ru.otus.hw.dto.calories.CaloriesTypeDto;
import ru.otus.hw.dto.diets.DietTypeDto;
import ru.otus.hw.dto.meal.MealCreateDto;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.meal.MealUpdateDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;

import java.util.List;

public interface MealService {
    List<MealDto> findAllByMealtimeTypeAndDietTypesContainsAndCaloriesTypesContains(
            MealtimeTypeDto mealtimeTypeDto, DietTypeDto dietTypeDto,
            CaloriesTypeDto caloriesTypeDto);

    MealDto findById(Long id);

    List<MealDto> findAll();

    MealDto create(MealCreateDto dto);

    MealDto update(MealUpdateDto dto);

    void delete(Long id);
}
