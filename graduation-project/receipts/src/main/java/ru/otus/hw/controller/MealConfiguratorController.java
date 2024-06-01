package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.proxies.PlanControllerProxy;

@RestController
@RequiredArgsConstructor
@EnableFeignClients("ru.otus.hw.proxies")
public class MealConfiguratorController {
    private final PlanControllerProxy proxy;

    @GetMapping("/api/meal-list/{diet_type_id}/{calories_type_id}/{day_count}")
    public String getMealsForDays(@PathVariable("diet_type_id") Long dietTypeId,
                                  @PathVariable("calories_type_id") Long caloriesTypeId,
                                  @PathVariable("day_count") int dayCount) {
        return proxy.mealList(dietTypeId, caloriesTypeId, dayCount);
    }

    @GetMapping("api/price-list")
    public String priceList() {
        return proxy.priceList();
    }
}
