package ru.otus.hw.mealsbot.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealsbot.models.Receipt;
import ru.otus.hw.mealsbot.models.ReceiptPosition;

@Component
@RequiredArgsConstructor
public class ReceiptConverter {
    private final ReceiptPositionConverter positionConverter;

    public String toString(Receipt receipt) {
        final StringBuilder positionBuilder = new StringBuilder();

        long sumCalories = 0L;
        for(ReceiptPosition position : receipt.getReceiptPositionsList()) {
            positionBuilder.append("- ").append(positionConverter.toString(position))
                    .append("\n");

            var mult = position.getQuantity();
            sumCalories += position.getServing().getCalories() * mult;
        }

        return receipt.getMealtime().getName() + "\n" +
                receipt.getName() + " (" +
                sumCalories + " ккал)\n" +
                positionBuilder;
    }
}
