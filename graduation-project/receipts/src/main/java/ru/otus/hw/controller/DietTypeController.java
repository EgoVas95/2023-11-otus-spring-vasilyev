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
import ru.otus.hw.dto.diets.DietTypeCreateDto;
import ru.otus.hw.dto.diets.DietTypeDto;
import ru.otus.hw.dto.diets.DietTypeUpdateDto;
import ru.otus.hw.services.diet.DietTypeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DietTypeController {
    private final DietTypeServiceImpl service;

    @GetMapping("/api/diet-types")
    public List<DietTypeDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/diet-types/{diet_type_id}")
    public DietTypeDto getById(@PathVariable("diet_type_id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/api/diet-types")
    @ResponseStatus(value = HttpStatus.CREATED)
    public DietTypeDto create(@Valid @RequestBody DietTypeCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/diet-types/{diet_type_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public DietTypeDto update(@PathVariable("diet_type_id") Long id,
                              @Valid @RequestBody DietTypeUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/diet-types/{diet_type_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("diet_type_id") Long id) {
        service.delete(id);
    }
}
