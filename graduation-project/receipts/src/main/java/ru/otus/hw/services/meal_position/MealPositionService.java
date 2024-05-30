package ru.otus.hw.services.meal_position;

import ru.otus.hw.dto.meal_positions.MealPositionCreateDto;
import ru.otus.hw.dto.meal_positions.MealPositionDto;
import ru.otus.hw.dto.meal_positions.MealPositionUpdateDto;

import java.util.List;

public interface MealPositionService {
    List<MealPositionDto> findAllByMealId(Long mealId);

    List<MealPositionDto> findAll();

    MealPositionDto findById(Long id);

    MealPositionDto create(MealPositionCreateDto dto);

    MealPositionDto update(MealPositionUpdateDto dto);

    void delete(Long id);
}
