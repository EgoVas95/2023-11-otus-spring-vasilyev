package ru.otus.hw.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Food;

@Component
@RequiredArgsConstructor
public class FoodConverter {
    public String toString(Food obj) {
        return "Id: %s, name: %s".formatted(obj.getId(),
                obj.getName());
    }
}
