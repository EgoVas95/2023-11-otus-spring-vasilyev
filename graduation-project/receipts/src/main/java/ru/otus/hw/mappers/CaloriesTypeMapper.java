package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.calories.CaloriesTypeCreateDto;
import ru.otus.hw.dto.calories.CaloriesTypeDto;
import ru.otus.hw.dto.calories.CaloriesTypeUpdateDto;
import ru.otus.hw.models.CaloriesType;

@Component
public class CaloriesTypeMapper {
    public CaloriesType toModel(CaloriesTypeDto dto) {
        return new CaloriesType(dto.getId(), dto.getCalories());
    }

    public CaloriesType toModel(CaloriesTypeCreateDto dto) {
        return new CaloriesType(dto.getId(), dto.getCalories());
    }

    public CaloriesType toModel(CaloriesTypeUpdateDto dto) {
        return new CaloriesType(dto.getId(), dto.getCalories());
    }

    public CaloriesTypeDto toDto(CaloriesType model) {
        return new CaloriesTypeDto(model.getId(), model.getCalories());
    }
}
