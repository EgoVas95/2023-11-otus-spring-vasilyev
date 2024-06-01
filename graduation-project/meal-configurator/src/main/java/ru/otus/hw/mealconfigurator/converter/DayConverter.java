package ru.otus.hw.mealconfigurator.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealconfigurator.dto.DayDto;

@Component
@RequiredArgsConstructor
public class DayConverter {

    private final DayPositionConverter positionConverter;

    public String convertDay(DayDto dayDto) {
        StringBuilder sb = new StringBuilder();
        dayDto.getPositionList().forEach(position ->
                sb.append(positionConverter.convertPosition(position))
                        .append("\r\n"));
        return sb.toString();
    }
}
