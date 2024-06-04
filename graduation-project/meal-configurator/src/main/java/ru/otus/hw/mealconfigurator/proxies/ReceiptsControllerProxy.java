package ru.otus.hw.mealconfigurator.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.mealconfigurator.configuration.feign.OAuthFeignConfig;
import ru.otus.hw.mealconfigurator.model.receipt_models.CaloriesType;
import ru.otus.hw.mealconfigurator.model.receipt_models.DietType;
import ru.otus.hw.mealconfigurator.model.receipt_models.Mealtime;
import ru.otus.hw.mealconfigurator.model.receipt_models.Receipt;

import java.util.List;

@FeignClient(name = "receipts", configuration = OAuthFeignConfig.class)
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
