package ru.otus.hw.mealconfigurator.dto.proxy_dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ReceiptDto {
    private Long id;

    private FoodDto foodDto;

    private String instruction;
}
