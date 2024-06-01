package ru.otus.hw.mealconfigurator.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealconfigurator.dto.PricePositionDto;
import ru.otus.hw.mealconfigurator.utils.RoundBigDecimals;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PricePositionConverter {

    private final RoundBigDecimals round;

    public String convertPricePosition(PricePositionDto pricePositionDto, BigDecimal quantity) {
        return "%s - %d %s.".formatted(pricePositionDto.getName(), round.round(quantity),
                pricePositionDto.getServingName());
    }
}
