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
import ru.otus.hw.models.CaloriesType;
import ru.otus.hw.services.calories.CaloriesTypeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CaloriesTypeController {
    private final CaloriesTypeServiceImpl service;

    @GetMapping("/api/calories-types")
    public List<CaloriesType> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/calories-types/{calories_type_id}")
    public CaloriesType getById(@PathVariable("calories_type_id") String id) {
        return service.findById(id);
    }

    @PostMapping("/api/calories-types")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CaloriesType create(@Valid @RequestBody CaloriesType caloriesType) {
        return service.create(caloriesType);
    }

    @PatchMapping("/api/calories-types/{calories_type_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CaloriesType update(@PathVariable("calories_type_id") String id,
                              @Valid @RequestBody CaloriesType caloriesType) {
        caloriesType.setId(id);
        return service.update(caloriesType);
    }

    @DeleteMapping("/api/calories-types/{calories_type_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("calories_type_id") String id) {
        service.delete(id);
    }
}
