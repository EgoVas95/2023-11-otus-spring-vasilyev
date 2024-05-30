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
import ru.otus.hw.dto.calories.CaloriesTypeCreateDto;
import ru.otus.hw.dto.calories.CaloriesTypeDto;
import ru.otus.hw.dto.calories.CaloriesTypeUpdateDto;
import ru.otus.hw.services.calories.CaloriesTypeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CaloriesTypeController {
    private final CaloriesTypeServiceImpl service;

    @GetMapping("/api/calories-types")
    public List<CaloriesTypeDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/calories-types/{calories_type_id}")
    public CaloriesTypeDto getById(@PathVariable("calories_type_id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/api/calories-types")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CaloriesTypeDto create(@Valid @RequestBody CaloriesTypeCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/calories-types/{calories_type_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CaloriesTypeDto update(@PathVariable("calories_type_id") Long id,
                              @Valid @RequestBody CaloriesTypeUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/calories-types/{calories_type_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("calories_type_id") Long id) {
        service.delete(id);
    }
}
