package ru.otus.hw.dto.mealtime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealtimeTypeDto {
    private Long id;

    private String name;
}
