package ru.otus.hw.mealconfigurator.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.mealconfigurator.configuration.OAuthFeignConfig;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.FoodDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.MealDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.MealPositionDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.MealtimeTypeDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.ServingDto;

import java.util.List;

@FeignClient(name = "receipts",
    configuration = OAuthFeignConfig.class)
public interface ReceiptsControllerProxy {

    @GetMapping("/api/mealtimes")
    List<MealtimeTypeDto> getMealTimeTypes();

    @GetMapping("/api/meals/{mealtime_id}/{diet_id}/{calories_id}")
    List<MealDto> getMeals(
            @PathVariable(name = "mealtime_id") Long mealtimeId,
            @PathVariable(name = "diet_id") Long dietId,
            @PathVariable(name = "calories_id") Long caloriesId);

    @GetMapping("/api/meal-positions/meal/{meal_id}")
    List<MealPositionDto> getMealPositionsById(@PathVariable("meal_id") Long mealId);

    @GetMapping("/api/servings/{serving_id}")
    ServingDto getServingsById(@PathVariable("serving_id") Long id);

    @GetMapping("/api/foods/{food_id}")
    FoodDto getFoodById(@PathVariable("food_id") Long id);
}
