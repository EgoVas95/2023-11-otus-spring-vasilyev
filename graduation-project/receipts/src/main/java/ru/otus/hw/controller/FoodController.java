package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.food.FoodCreateDto;
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.food.FoodUpdateDto;
import ru.otus.hw.proxies.PlanControllerProxy;
import ru.otus.hw.services.food.FoodServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@EnableFeignClients("ru.otus.hw.proxies")
public class FoodController {
    private final FoodServiceImpl service;

    private final PlanControllerProxy proxy;

    @GetMapping("/api/foods")
    public List<FoodDto> findAll() {
        String response = proxy.plans();
        System.out.println(response);
        return service.findAll();
    }

    @GetMapping("/api/foods/name/{name}")
    public List<FoodDto> getByName(@PathVariable("name") String name) {
        return service.findByName(name);
    }

    @GetMapping("/api/foods/{food_id}")
    public FoodDto getById(@PathVariable("food_id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/api/foods")
    @ResponseStatus(value = HttpStatus.CREATED)
    public FoodDto create(@Valid @RequestBody FoodCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/foods/{food_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public FoodDto update(@PathVariable("food_id") Long id,
                          @Valid @RequestBody FoodUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/foods/{food_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("food_id") Long id) {
        service.delete(id);
    }
}
