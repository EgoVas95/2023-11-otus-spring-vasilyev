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
import ru.otus.hw.dto.serving.ServingCreateDto;
import ru.otus.hw.dto.serving.ServingDto;
import ru.otus.hw.dto.serving.ServingUpdateDto;
import ru.otus.hw.services.serving.ServingServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServingController {
    private final ServingServiceImpl service;

    @GetMapping("/api/servings")
    public List<ServingDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/servings/{serving_id}")
    public ServingDto findById(@PathVariable("serving_id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/api/servings/food/{food_id}")
    public List<ServingDto> findByFoodId(@PathVariable("food_id") Long id) {
        return service.findByFoodId(id);
    }

    @GetMapping("/api/servings/name/{serving_name}")
    public List<ServingDto> findByServingName(@PathVariable("serving_name") String name) {
        return service.findByName(name);
    }

    @GetMapping("/api/servings/calories/{less_than}")
    public List<ServingDto> findByLessOrEqThan(@PathVariable("less_than") BigDecimal calories) {
        return service.findByCaloriesLessOrEqThan(calories);
    }

    @PostMapping("/api/servings")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ServingDto create(@Valid @RequestBody ServingCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/servings/{serving_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ServingDto update(@PathVariable("serving_id") Long id,
            @Valid @RequestBody ServingUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/servings/{serving_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("serving_id") Long id) {
        service.delete(id);
    }
}
