package ru.otus.hw.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.dto.serving.ServingDto;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IngredientDto {
    private Long id;

    private ReceiptDto receiptDto;

    private ServingDto servingDto;

    private BigDecimal quantity;
}
