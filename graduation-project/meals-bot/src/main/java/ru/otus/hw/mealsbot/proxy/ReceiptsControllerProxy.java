package ru.otus.hw.mealsbot.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.mealsbot.models.CaloriesType;
import ru.otus.hw.mealsbot.models.DietType;
import ru.otus.hw.mealsbot.models.Mealtime;
import ru.otus.hw.mealsbot.models.Receipt;

import java.util.List;

@FeignClient(name = "receipts", contextId = "bot-receipts",
    url = "http://localhost:8181")
public interface ReceiptsControllerProxy {

    @GetMapping("/api/calories-types")
    List<CaloriesType> getCaloriesTypes();

    @GetMapping("/api/diet-types")
    List<DietType> getDietTypes();

    @GetMapping("/api/mealtimes")
    List<Mealtime> getMealtimes();

    @GetMapping("/api/receipts/food/{mealtime_id}/{diet_type_id}/{calories_type_id}")
    List<Receipt> getReceiptForMeal(@PathVariable("mealtime_id") String mealtimeId,
                                    @PathVariable("diet_type_id") String dietTypeId,
                                    @PathVariable("calories_type_id") String caloriesTypeId);
}
