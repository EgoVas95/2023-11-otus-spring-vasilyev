package ru.otus.hw.services.calories;

import ru.otus.hw.dto.calories.CaloriesTypeCreateDto;
import ru.otus.hw.dto.calories.CaloriesTypeDto;
import ru.otus.hw.dto.calories.CaloriesTypeUpdateDto;

import java.util.List;

public interface CaloriesService {
    CaloriesTypeDto findById(Long id);

    List<CaloriesTypeDto> findAll();

    CaloriesTypeDto create(CaloriesTypeCreateDto dto);

    CaloriesTypeDto update(CaloriesTypeUpdateDto dto);

    void delete(Long id);
}
