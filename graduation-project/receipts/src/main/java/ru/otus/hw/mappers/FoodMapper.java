package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.food.FoodCreateDto;
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.food.FoodUpdateDto;
import ru.otus.hw.models.Food;

@Component
public class FoodMapper {
    public Food toModel(FoodDto foodDto) {
        return new Food(foodDto.getId(), foodDto.getName());
    }

    public Food toModel(FoodCreateDto foodDto) {
        return new Food(null, foodDto.getName());
    }

    public Food toModel(FoodUpdateDto foodDto) {
        return new Food(foodDto.getId(), foodDto.getName());
    }

    public FoodDto toDto(Food food) {
        return new FoodDto(food.getId(), food.getName());
    }
}
