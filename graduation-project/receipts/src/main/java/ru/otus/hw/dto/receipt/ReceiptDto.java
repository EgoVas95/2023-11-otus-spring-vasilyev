package ru.otus.hw.dto.receipt;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.food.FoodDto;


@Data
@AllArgsConstructor
public class ReceiptDto {
    private Long id;

    private FoodDto foodDto;

    private String instruction;
}
