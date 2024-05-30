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
import ru.otus.hw.dto.meal.MealCreateDto;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.meal.MealUpdateDto;
import ru.otus.hw.services.meal.MealServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealController {
    private final MealServiceImpl service;

    @GetMapping("/api/meals/{mealtime_id}/{diet_id}/{calories_id}")
    public List<MealDto> findAllByParams(
            @PathVariable(name = "mealtime_id") Long mealtimeId,
            @PathVariable(name = "diet_id") Long dietId,
            @PathVariable(name = "calories_id") Long caloriesId) {
        return service.findAllBySeveralParams(mealtimeId, dietId, caloriesId);
    }

    @GetMapping("/api/meals")
    public List<MealDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/meals/{meal_id}")
    public MealDto getById(@PathVariable("meal_id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/api/meals")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MealDto create(@Valid @RequestBody MealCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/meals/{meal_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public MealDto update(@PathVariable("meal_id") Long id,
                              @Valid @RequestBody MealUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/meals/{meal_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("meal_id") Long id) {
        service.delete(id);
    }
}
