package ru.otus.hw.mealconfigurator.dto.proxy_dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MealPositionDto {
    private MealtimeTypeDto mealtimeTypeDto;

    private ServingDto serving;

    private BigDecimal quantity;
}
