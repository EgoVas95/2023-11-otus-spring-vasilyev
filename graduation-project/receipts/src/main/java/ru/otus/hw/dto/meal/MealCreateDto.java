package ru.otus.hw.dto.meal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.calories.CaloriesTypeDto;
import ru.otus.hw.dto.diets.DietTypeDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;

@Data
@AllArgsConstructor
public class MealCreateDto {
    @NotNull(message = "Тип приёма пищи не может быть пустым!")
    private MealtimeTypeDto mealtimeTypeDto;

    @NotNull(message = "Тип диеты не может быть пустым!")
    private DietTypeDto dietTypeDto;

    @NotNull(message = "Тип калоража не может быть пустым!")
    private CaloriesTypeDto caloriesDto;
}
