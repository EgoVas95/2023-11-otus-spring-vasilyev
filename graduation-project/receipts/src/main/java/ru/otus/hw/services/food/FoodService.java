package ru.otus.hw.services.food;

import ru.otus.hw.dto.food.FoodCreateDto;
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.food.FoodUpdateDto;

import java.util.List;

public interface FoodService {
    List<FoodDto> findByName(String name);

    FoodDto findById(Long id);

    List<FoodDto> findAll();

    FoodDto create(FoodCreateDto dto);

    FoodDto update(FoodUpdateDto dto);

    void delete(Long id);
}
