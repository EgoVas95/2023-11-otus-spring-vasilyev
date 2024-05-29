package ru.otus.hw.dto.meal_positions;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.serving.ServingDto;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MealPositionDto {
    private Long id;

    private MealDto meal;

    private ServingDto serving;

    private BigDecimal quantity;
}
