package ru.otus.hw.mealsbot.converter;

import org.springframework.stereotype.Component;
import ru.otus.hw.mealsbot.models.ReceiptPosition;

@Component
public class ReceiptPositionConverter {
    public String toString(ReceiptPosition position) {
        return "%s: %d %s".formatted(position.getServing().getFood().getName(),
                position.getQuantity(), position.getServing().getName());
    }
}
