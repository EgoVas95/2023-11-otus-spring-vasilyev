package ru.otus.hw.dto.food;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodDto {
    private Long id;

    private String name;
}
