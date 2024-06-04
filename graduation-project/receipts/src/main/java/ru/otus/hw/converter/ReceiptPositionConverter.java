package ru.otus.hw.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.ReceiptPosition;

@Component
@RequiredArgsConstructor
public class ReceiptPositionConverter {
    private final ServingConverter converter;

    public String toString(ReceiptPosition position) {
        return "Serving: { %s }, quantity: %d"
                .formatted(converter.toString(position.getServing()),
                        position.getQuantity());
    }
}
