package ru.otus.hw.mealconfigurator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.mealconfigurator.proxies.ReceiptsControllerProxy;
import ru.otus.hw.mealconfigurator.services.DayConfiguratorService;
import ru.otus.hw.mealconfigurator.services.PriceService;

@RestController
@RequiredArgsConstructor
public class MealConfigureController {


    private final DayConfiguratorService dayConfiguratorService;

    private final PriceService priceService;

    @GetMapping("api/meal-list/{diet_type_id}/{calories_type_id}/{day_count}")
    public String getMealsForDays(@PathVariable("diet_type_id") Long dietTypeId,
                                        @PathVariable("calories_type_id") Long calorieTypeId,
                                        @PathVariable("day_count") int dayCount) {
        return dayConfiguratorService.getMealString(proxy, diet_type_id, calories_type_id, day_count);
    }

    @GetMapping("api/price-list")
    public String getPriceDays() {
        return priceService.getStringOfPriceList();
    }
}
