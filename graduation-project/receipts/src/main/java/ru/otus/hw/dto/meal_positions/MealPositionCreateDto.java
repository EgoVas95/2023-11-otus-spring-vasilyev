package ru.otus.hw.dto.meal_positions;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.serving.ServingDto;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MealPositionCreateDto {
    @NotNull(message = "Тип приёма пищи не может быть пустым!")
    private MealDto meal;

    @NotNull(message = "Порция не может быть пустой!")
    private ServingDto serving;

    @NotNull(message = "Количество не может быть пустым!")
    @Positive(message = "Количество должно быть больше 0!")
    private BigDecimal quantity;
}
