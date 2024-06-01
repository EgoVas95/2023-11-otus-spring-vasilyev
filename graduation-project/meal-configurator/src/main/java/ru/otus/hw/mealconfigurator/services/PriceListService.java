package ru.otus.hw.mealconfigurator.services;

import ru.otus.hw.mealconfigurator.dto.DayDto;
import ru.otus.hw.mealconfigurator.model.PriceList;

import java.util.List;

public interface PriceListService {
    void save(List<DayDto> dtoList);

    PriceList findForUser();
}
