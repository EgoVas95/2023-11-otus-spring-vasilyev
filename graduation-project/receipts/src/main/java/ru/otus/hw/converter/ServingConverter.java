package ru.otus.hw.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Serving;

@Component
@RequiredArgsConstructor
public class ServingConverter {
    private final FoodConverter converter;

    public String toString(Serving serving) {
        return "Id: %s, name: %s, food = {%s}, calories: %d"
                .formatted(serving.getId(), serving.getName(),
                        converter.toString(serving.getFood()),
                        serving.getCalories());
    }
}
