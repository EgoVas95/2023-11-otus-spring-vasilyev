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
import ru.otus.hw.dto.meal_positions.MealPositionCreateDto;
import ru.otus.hw.dto.meal_positions.MealPositionDto;
import ru.otus.hw.dto.meal_positions.MealPositionUpdateDto;
import ru.otus.hw.services.meal_position.MealPositionServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealPositionController {
    private final MealPositionServiceImpl service;

    @GetMapping("/api/meal-positions/meal/{meal_id}")
    public List<MealPositionDto> findAllByMealId(@PathVariable("meal_id") Long mealId) {
        return service.findAllByMealId(mealId);
    }

    @GetMapping("/api/meal-positions")
    public List<MealPositionDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/meal-positions/{meal_position_id}")
    public MealPositionDto getById(@PathVariable("meal_position_id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/api/meal-positions")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MealPositionDto create(@Valid @RequestBody MealPositionCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/meal-positions/{meal_position_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public MealPositionDto update(@PathVariable("meal_position_id") Long id,
                              @Valid @RequestBody MealPositionUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/meal-positions/{meal_position_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("meal_position_id") Long id) {
        service.delete(id);
    }
}
