package ru.otus.hw.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Mealtime;

@Component
@RequiredArgsConstructor
public class MealtimeConverter {
    public String toString(Mealtime obj) {
        return "Id: %s, name: %s".formatted(obj.getId(),
                obj.getName());
    }
}
