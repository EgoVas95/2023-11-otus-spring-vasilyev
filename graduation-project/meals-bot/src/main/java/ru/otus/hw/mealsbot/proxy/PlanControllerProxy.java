package ru.otus.hw.mealsbot.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.mealsbot.models.Day;
import ru.otus.hw.mealsbot.models.PricePosition;

import java.util.List;

@FeignClient(name = "meal-configurator", contextId = "bot-meals",
    url = "http://localhost:8282")
public interface PlanControllerProxy {
    @GetMapping("/api/meal-list/{diet_type_id}/{calories_type_id}/{day_count}")
    List<Day> mealList(@PathVariable("diet_type_id") String dietTypeId,
                       @PathVariable("calories_type_id") String caloriesTypeId,
                       @PathVariable("day_count") int dayCount);


    @GetMapping("/api/meal-list")
    List<Day> getMealsForUser();

    @GetMapping("/api/meal-list/{date}")
    Day getMealsOnDayForUser(@PathVariable("date") String dateStr);

    @GetMapping("/api/buy-list")
    List<PricePosition> getBuyMap();

    @GetMapping("/api/buy-list/{date}")
    List<PricePosition> getBuyMapForDate(@PathVariable("date") String dateStr);
}
