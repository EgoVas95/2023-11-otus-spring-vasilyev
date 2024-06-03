package ru.otus.hw.mealsbot.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealsbot.models.Day;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DayConverter {
    private final ReceiptConverter converter;

    public String convertList(List<Day> dayList) {
        StringBuilder sb = new StringBuilder();
        for (var day : dayList) {
            sb.append("\n").append(convertDay(day))
                    .append("\n");
        }
        return sb.toString();
    }

    public String convertDay(Day day) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        StringBuilder sb = new StringBuilder()
                .append(day.getDate().format(dateTimeFormatter))
                .append(":\n");
        for (var receipt : day.getReceiptList()) {
            sb.append("- ").append(converter.toString(receipt))
                    .append("\n");
        }

        return sb.toString();
    }
}
