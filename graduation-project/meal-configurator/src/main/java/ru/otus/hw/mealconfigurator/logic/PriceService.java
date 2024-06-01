package ru.otus.hw.mealconfigurator.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealconfigurator.converter.PriceListConverter;
import ru.otus.hw.mealconfigurator.services.PriceListServiceImpl;

@Component
@RequiredArgsConstructor
public class PriceService {
    private final PriceListServiceImpl service;

    private final PriceListConverter converter;

    public String getPriceList() {

    }
}
