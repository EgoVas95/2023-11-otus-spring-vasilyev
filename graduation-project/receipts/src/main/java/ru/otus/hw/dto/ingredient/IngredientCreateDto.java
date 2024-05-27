package ru.otus.hw.dto.ingredient;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IngredientCreateDto {

    @NotNull(message = "ID рецепта не может быть пустым!")
    private Long receiptId;

    @NotNull(message = "ID порции не может быть пустым!")
    private Long servingId;

    @NotNull(message = "Количество не может быть пустым!")
    @Positive(message = "Количество должно быть больше 0!")
    private BigDecimal quantity;
}
