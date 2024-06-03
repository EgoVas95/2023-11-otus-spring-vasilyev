package ru.otus.hw.services.food;

import ru.otus.hw.models.Food;

import java.util.List;

public interface FoodService {
    Food findById(String id);

    List<Food> findAll();

    Food create(Food food);

    Food update(Food food);

    void delete(String id);
}
