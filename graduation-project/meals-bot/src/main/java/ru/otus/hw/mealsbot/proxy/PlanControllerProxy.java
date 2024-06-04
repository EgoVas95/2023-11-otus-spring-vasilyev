package ru.otus.hw.mealsbot.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.mealsbot.models.Day;
import ru.otus.hw.mealsbot.models.PricePosition;

import java.util.List;

@FeignClient(name = "meal-configurator", contextId = "bot-meals",
    url = "${meals-proxy.configurator}")
public interface PlanControllerProxy {
    @GetMapping("/api/meal-list/{username}/{diet_type_id}/{calories_type_id}/{day_count}")
    List<Day> mealList(@PathVariable("username") String username,
                                     @PathVariable("diet_type_id") String dietTypeId,
                                     @PathVariable("calories_type_id") String caloriesTypeId,
                                     @PathVariable("day_count") int dayCount);


    @GetMapping("/api/meal-list/{username}")
    List<Day> getMealsForUser(@PathVariable("username") String username);

    @GetMapping("/api/meal-list/{username}/{date}")
    Day getMealsOnDayForUser(@PathVariable("username") String username,
                                    @PathVariable("date") String dateStr);

    @GetMapping("/api/buy-list/{username}")
    List<PricePosition> geBuyMap(@PathVariable("username") String username);

    @GetMapping("/api/buy-list/{username}/{date}")
    List<PricePosition> geBuyMap(@PathVariable("username") String username,
                                        @PathVariable("date") String dateStr);
}
