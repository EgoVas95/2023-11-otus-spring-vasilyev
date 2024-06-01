package ru.otus.hw.mealconfigurator.dto.proxy_dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodDto {
    private Long id;

    private String name;
}
