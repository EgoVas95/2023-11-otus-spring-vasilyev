package ru.otus.hw.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Receipt;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReceiptConverter {
    private final CaloriesTypeConverter caloriesTypeConverter;

    private final DietConverter dietConverter;

    private final MealtimeConverter mealtimeConverter;

    private final ReceiptPositionConverter receiptPositionConverter;

    public String toString(Receipt receipt) {
        var dietStr = dietConverter.toString(receipt.getDietType());
        var caloriestStr = caloriesTypeConverter.toString(receipt.getCaloriesType());
        var mealtime = mealtimeConverter.toString(receipt.getMealtime());
        var positionStr = receipt.getReceiptPositionsList().stream()
                .map(receiptPositionConverter::toString)
                .collect(Collectors.joining(", "));

        return ("Id: %s, name: %s, diet: { %s }, calories: { %s }, " +
                "mealtime: { %s }, positions: { %s }")
                .formatted(receipt.getId(), receipt.getName(), dietStr,
                        caloriestStr, mealtime, positionStr);
    }
}
