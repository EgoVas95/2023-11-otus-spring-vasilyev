package ru.otus.hw.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.CaloriesType;

@Component
@RequiredArgsConstructor
public class CaloriesTypeConverter {
    public String toString(CaloriesType caloriesType) {
        return "Id: %s, calories: %d".formatted(caloriesType.getId(),
                caloriesType.getCalories());
    }
}
