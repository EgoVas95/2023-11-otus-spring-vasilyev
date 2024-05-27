package ru.otus.hw.dto.serving;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.food.FoodDto;

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
