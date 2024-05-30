package ru.otus.hw.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.configurations.OAuthFeignConfig;

@FeignClient(name = "meal-configurator",
        configuration = OAuthFeignConfig.class)
public interface PlanControllerProxy {

    @GetMapping("/api/plans")
    String plans();
}
