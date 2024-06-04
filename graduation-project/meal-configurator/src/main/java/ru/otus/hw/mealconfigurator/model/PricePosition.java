package ru.otus.hw.mealconfigurator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.hw.mealconfigurator.model.receipt_models.Serving;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PricePosition {
    private Serving serving;

    private Long quantity;
}
