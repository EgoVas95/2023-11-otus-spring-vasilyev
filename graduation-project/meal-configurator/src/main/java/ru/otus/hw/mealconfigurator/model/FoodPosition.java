package ru.otus.hw.mealconfigurator.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class FoodPosition {
    private String foodName;

    private String servingName;

    private Long servingId;

    private BigDecimal quantity;
}
