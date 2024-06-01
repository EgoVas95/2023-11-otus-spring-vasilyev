package ru.otus.hw.mealconfigurator.dto.proxy_dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ServingDto {
    private Long id;

    private String name;

    private FoodDto foodDto;

    private BigDecimal calories;

    private BigDecimal proteins;

    private BigDecimal fats;

    private BigDecimal carbohydrates;
}
