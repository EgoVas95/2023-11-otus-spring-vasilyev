package ru.otus.hw.mealconfigurator.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealconfigurator.dto.DayPositionDto;
import ru.otus.hw.mealconfigurator.utils.RoundBigDecimals;

@Component
@RequiredArgsConstructor
public class DayPositionConverter {

    private final RoundBigDecimals round;

    public String convertPosition(DayPositionDto dayPositionDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(dayPositionDto.getMealtimeTypeDto().getName())
                .append(": \r\n");
        dayPositionDto.getServingQuantityMap().keySet().forEach(serving ->
                sb.append(" -").append(serving.getFoodDto().getName()).append("\t")
                .append(serving.getName()).append("\t")
                .append(round.round(
                        dayPositionDto.getServingQuantityMap().get(serving)
                )));
        sb.append("\r\n").append("Итого: \r\n")
                .append("ккал = ").append(round.round(
                        dayPositionDto.getCaloriesSum())).append("\t")
                .append("белки = ").append(round.round(
                        dayPositionDto.getProteinSum())).append("\t")
                .append("жиры = ").append(round.round(
                                dayPositionDto.getFatsSum())).append("\t")
                .append("углеводы = ").append(round.round(
                        dayPositionDto.getCarbohydratesSum())).append("\t");

        return sb.toString();
    }
}
