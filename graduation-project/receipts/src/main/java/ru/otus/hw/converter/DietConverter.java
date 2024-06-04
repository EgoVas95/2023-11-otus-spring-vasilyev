package ru.otus.hw.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.DietType;

@Component
@RequiredArgsConstructor
public class DietConverter {
    public String toString(DietType obj) {
        return "Id: %s, name: %s".formatted(obj.getId(),
                obj.getName());
    }
}
