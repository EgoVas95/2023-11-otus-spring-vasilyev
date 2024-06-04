package ru.otus.hw.mealconfigurator.services;

import ru.otus.hw.mealconfigurator.model.PricePosition;
import ru.otus.hw.mealconfigurator.model.receipt_models.Serving;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BuyMapGenerator {
    List<PricePosition> generateBuyList(String username);

    Map<Serving, Long> generateBuyMapByDay(String username, LocalDate date);
}
