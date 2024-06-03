package ru.otus.hw.services.calories;

import ru.otus.hw.models.CaloriesType;

import java.util.List;

public interface CaloriesTypeService {
    CaloriesType findById(String id);

    List<CaloriesType> findAll();

    CaloriesType create(CaloriesType caloriesType);

    CaloriesType update(CaloriesType caloriesType);

    void delete(String id);
}
