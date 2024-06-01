package ru.otus.hw.mealconfigurator.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealconfigurator.dto.DayDto;
import ru.otus.hw.mealconfigurator.dto.DayPositionDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.MealDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.MealtimeTypeDto;
import ru.otus.hw.mealconfigurator.proxies.ReceiptsControllerProxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.random.RandomGenerator;

@Component
@EnableFeignClients("ru.otus.hw.mealconfigurator.proxies")
@RequiredArgsConstructor
public class DaysGenerator {
    private final ReceiptsControllerProxy proxy;

    public List<DayDto> generateDaysList(Long diet, Long calories, int dayCount) {
        if (dayCount <= 0) {
            return Collections.emptyList();
        }

        var mealtimeTypes = proxy.getMealTimeTypes();
        final List<DayDto> result = new ArrayList<>(dayCount);

        for (int idx = 0; idx < dayCount; idx++) {
            result.add(getMealForDay(mealtimeTypes, diet, calories));
        }
        return result;
    }

    private DayDto getMealForDay(List<MealtimeTypeDto> mealtimeTypes,
                                 Long dietType, Long caloriesType) {
        var result = new DayDto();
        for (var mealtimeType : mealtimeTypes) {
            var meal = getRandomMeal(mealtimeType.getId(), dietType, caloriesType);
            if (meal == null) {
                continue;
            }
            var position = getDayPosition(meal);
            result.getPositionList().add(position);
        }
        return result;
    }

    private DayPositionDto getDayPosition(MealDto mealDto) {
        var mealPositions = proxy.getMealPositionsById(mealDto.getId());

        DayPositionDto result = new DayPositionDto(mealDto.getMealtimeTypeDto());
        for (var position : mealPositions) {
            result.addServing(position.getServing(), position.getQuantity());
        }
        return result;
    }

    private MealDto getRandomMeal(Long mealtimeType, Long dietType, Long caloriesType) {
        var meals = proxy.getMeals(mealtimeType, dietType, caloriesType);
        if (meals == null || meals.isEmpty()) {
            return null;
        }

        int randomMealIdx = RandomGenerator.getDefault().nextInt(0, meals.size());
        return meals.get(randomMealIdx);
    }
}
