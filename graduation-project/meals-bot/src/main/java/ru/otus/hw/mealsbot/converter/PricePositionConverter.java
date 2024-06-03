package ru.otus.hw.mealsbot.converter;

import org.springframework.stereotype.Component;
import ru.otus.hw.mealsbot.models.PricePosition;

import java.util.List;

@Component
public class PricePositionConverter {

    public String toString(List<PricePosition> positionList) {
        StringBuilder sb = new StringBuilder();
        int idx = 1;
        for (var pos : positionList) {
            sb.append(idx++).append(") ").append(toString(pos)).append("\n");
        }
        return sb.toString();
    }

    public String toString(PricePosition position) {
        return "%s - %d %s".formatted(position.getServing().getFood().getName(),
                position.getQuantity(), position.getServing().getName());
    }
}
