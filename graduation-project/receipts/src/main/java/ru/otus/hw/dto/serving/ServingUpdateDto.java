package ru.otus.hw.dto.serving;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ServingUpdateDto {
    @NotNull(message = "ID порции не может быть пустым!")
    private Long id;

    @NotBlank(message = "Наименование порции не может быть пустым!")
    private String name;

    @NotNull(message = "ID продукта не может быть пустым!")
    private Long foodId;

    @NotNull(message = "Количество белка не должно быть пустым!")
    @PositiveOrZero(message = "Количество килокалорий не должно быть меньше 0!")
    private BigDecimal calories;

    @NotNull(message = "Количество белка не должно быть пустым!")
    @PositiveOrZero(message = "Количество белка не должно быть меньше 0!")
    private BigDecimal proteins;

    @NotNull(message = "Количество жиров не должно быть пустым!")
    @PositiveOrZero(message = "Количество жиров не должно быть меньше 0!")
    private BigDecimal fats;

    @NotNull(message = "Количество углеводов не должно быть  пустым!")
    @PositiveOrZero(message = "Количество углеводов не должно быть меньше 0!")
    private BigDecimal carbohydrates;
}
