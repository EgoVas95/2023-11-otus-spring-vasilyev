package ru.otus.hw.mealconfigurator.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealconfigurator.converter.MealListConverter;
import ru.otus.hw.mealconfigurator.services.MealListServiceImpl;
import ru.otus.hw.mealconfigurator.services.PriceListServiceImpl;

@Component
@RequiredArgsConstructor
public class MealListService {

    private final DaysGenerator daysGenerator;

    private final MealListConverter mealListConverter;

    private final MealListServiceImpl mealListService;

    private final PriceListServiceImpl priceListService;

    public String generateMeals(Long dietTypeId, Long calorieTypeId,
                                     int dayCount) {
        var dayList = daysGenerator.generateDaysList(dietTypeId, calorieTypeId, dayCount);
        var mealList = mealListService.save(dayList);
        priceListService.save(dayList);

        return mealListConverter.mealListToString(mealList);
    }
}
