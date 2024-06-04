package ru.otus.hw.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.configuration.feign.OAuthFeignConfig;
import ru.otus.hw.models.meals_configurator.Day;
import ru.otus.hw.models.meals_configurator.PricePosition;

import java.util.List;

@FeignClient(name = "meal-configurator", configuration = {OAuthFeignConfig.class})
public interface PlanControllerProxy {

    @GetMapping("/api/meal-list/{diet_type_id}/{calories_type_id}/{day_count}")
    List<Day> mealList(@PathVariable("diet_type_id") String dietTypeId,
                       @PathVariable("calories_type_id") String caloriesTypeId,
                       @PathVariable("day_count") int dayCount);

    @GetMapping("/api/buy-list")
    List<PricePosition> getBuyMap();

    @GetMapping("/api/buy-list/{date}")
    List<PricePosition> getBuyMap(@PathVariable("date") String dateStr);
}
