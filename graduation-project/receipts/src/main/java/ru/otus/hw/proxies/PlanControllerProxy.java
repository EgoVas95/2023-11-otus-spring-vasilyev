package ru.otus.hw.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.configurations.feign.OAuthFeignConfig;

@FeignClient(name = "meal-configurator",
    configuration = OAuthFeignConfig.class)
public interface PlanControllerProxy {

    @GetMapping("api/meal-list/{diet_type_id}/{calories_type_id}/{day_count}")
    String mealList(@PathVariable("diet_type_id") Long dietTypeId,
                    @PathVariable("calories_type_id") Long caloriesTypeId,
                    @PathVariable("day_count") int dayCount);

    @GetMapping("api/price-list")
    String priceList();
}
