package ru.otus.hw.dto.calories;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CaloriesTypeUpdateDto {
    @NotNull(message = "ID типа калоража не может быть пустым!")
    private Long id;

    @NotNull(message = "Количество ккал не может быть пустым!")
    @PositiveOrZero(message = "Количество ккал не может быть отрицательным!")
    private BigDecimal calories;
}
