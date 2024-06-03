package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.Food;
import ru.otus.hw.services.food.FoodServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodServiceImpl service;

    @GetMapping("/api/foods")
    public List<Food> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/foods/{food_id}")
    public Food getById(@PathVariable("food_id") String id) {
        return service.findById(id);
    }

    @PostMapping("/api/foods")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Food create(@Valid @RequestBody Food food) {
        return service.create(food);
    }

    @PatchMapping("/api/foods/{food_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Food update(@PathVariable("food_id") String id,
                          @Valid @RequestBody Food food) {
        food.setId(id);
        return service.update(food);
    }

    @DeleteMapping("/api/foods/{food_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("food_id") String id) {
        service.delete(id);
    }
}
