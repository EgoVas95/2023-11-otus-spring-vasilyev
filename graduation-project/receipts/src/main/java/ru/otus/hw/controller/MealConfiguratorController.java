package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.meals_configurator.Day;
import ru.otus.hw.models.meals_configurator.PricePosition;
import ru.otus.hw.proxies.PlanControllerProxy;
import ru.otus.hw.repositories.CaloriesTypeRepository;
import ru.otus.hw.repositories.DietTypeRepository;

import java.util.List;

@RestController
@EnableFeignClients("ru.otus.hw.proxies")
@RequiredArgsConstructor
public class MealConfiguratorController {
    private final PlanControllerProxy proxy;

    private final DietTypeRepository dietTypeRepository;

    private final CaloriesTypeRepository caloriesTypeRepository;

    @GetMapping("/api/meal-configurator/{diet_type_id}/{calories_type_id}/{day_count}")
    public List<Day> getMealsForDays(@PathVariable("diet_type_id") String dietTypeId,
                                     @PathVariable("calories_type_id") String caloriesTypeId,
                                     @PathVariable("day_count") int dayCount) {
        return proxy.mealList(dietTypeId, caloriesTypeId, dayCount);
    }

    @GetMapping("/api/buy-list")
    public List<PricePosition> priceList() {
        return proxy.getBuyMap();
    }

    @GetMapping("/api/buy-list/{date}")
    public List<PricePosition> geBuyMap(@PathVariable("date") String dateStr) {
        return proxy.getBuyMap(dateStr);
    }
}
