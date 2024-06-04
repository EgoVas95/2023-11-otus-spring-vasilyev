package ru.otus.hw.mealsbot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Receipt {
    private String id;

    private String name;

    private CaloriesType caloriesType;

    private DietType dietType;

    private Mealtime mealtime;

    private List<ReceiptPosition> receiptPositionsList;
}
