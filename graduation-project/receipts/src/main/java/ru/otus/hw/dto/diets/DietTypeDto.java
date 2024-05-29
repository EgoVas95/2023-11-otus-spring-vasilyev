package ru.otus.hw.dto.diets;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DietTypeDto {
    private Long id;

    private String name;
}
