package ru.otus.hw.mealconfigurator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class DayPosition {
    private String mealtimeName;

    private List<FoodPosition> foodPositions;

    private BigDecimal caloriesSum = BigDecimal.ZERO;

    private BigDecimal proteinSum = BigDecimal.ZERO;

    private BigDecimal fatsSum = BigDecimal.ZERO;

    private BigDecimal carbohydratesSum = BigDecimal.ZERO;
}
