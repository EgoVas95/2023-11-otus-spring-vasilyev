package ru.otus.hw.models.meals_configurator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.hw.models.Receipt;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Day {
    private String id;

    private String userId;

    private LocalDate date;

    private List<Receipt> receiptList;
}
