package ru.otus.hw.dto.calories;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CaloriesTypeDto {
    private Long id;

    private BigDecimal calories;
}
