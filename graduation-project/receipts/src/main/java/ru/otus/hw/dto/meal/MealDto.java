package ru.otus.hw.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.calories.CaloriesTypeDto;
import ru.otus.hw.dto.diets.DietTypeDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;

import java.util.List;

@Data
@AllArgsConstructor
public class MealDto {
    private Long id;

    private MealtimeTypeDto mealtimeTypeDto;

    private DietTypeDto dietTypeDto;

    private CaloriesTypeDto caloriesDto;
}
