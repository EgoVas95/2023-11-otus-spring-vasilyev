package ru.otus.hw.mealconfigurator.dto.proxy_dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealDto {
    private Long id;

    private MealtimeTypeDto mealtimeTypeDto;

    private DietTypeDto dietTypeDto;

    private CaloriesTypeDto caloriesDto;
}
