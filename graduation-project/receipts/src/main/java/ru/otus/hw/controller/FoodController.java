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
import ru.otus.hw.dto.food.FoodCreateDto;
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.food.FoodUpdateDto;
import ru.otus.hw.services.food.FoodServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodServiceImpl foodService;

    @GetMapping("/api/foods")
    public List<FoodDto> allFoodList() {
        return foodService.findAll();
    }

    @GetMapping("/api/foods/name/{name}")
    public List<FoodDto> getFoodsByName(@PathVariable("name") String name) {
        return foodService.findByName(name);
    }

    @GetMapping("/api/foods/{food_id}")
    public FoodDto getFoodsById(@PathVariable("food_id") Long id) {
        return foodService.findById(id);
    }

    @PostMapping("/api/foods")
    @ResponseStatus(value = HttpStatus.CREATED)
    public FoodDto createFood(@Valid @RequestBody FoodCreateDto dto) {
        return foodService.create(dto);
    }

    @PatchMapping("/api/foods/{food_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public FoodDto updateFood(@PathVariable("food_id") Long foodId,
                              @Valid @RequestBody FoodUpdateDto dto) {
        dto.setId(foodId);
        return foodService.update(dto);
    }

    @DeleteMapping("/api/foods/{food_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable("food_id") Long foodId) {
        foodService.delete(foodId);
    }
}
