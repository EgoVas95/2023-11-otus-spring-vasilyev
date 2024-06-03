package ru.otus.hw.mealsbot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
