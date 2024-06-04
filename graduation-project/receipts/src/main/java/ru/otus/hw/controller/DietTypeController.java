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
import ru.otus.hw.models.DietType;
import ru.otus.hw.services.diet.DietTypeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DietTypeController {
    private final DietTypeServiceImpl service;

    @GetMapping("/api/diet-types")
    public List<DietType> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/diet-types/{diet_type_id}")
    public DietType getById(@PathVariable("diet_type_id") String id) {
        return service.findById(id);
    }

    @PostMapping("/api/diet-types")
    @ResponseStatus(value = HttpStatus.CREATED)
    public DietType create(@Valid @RequestBody DietType dietType) {
        return service.create(dietType);
    }

    @PatchMapping("/api/diet-types/{diet_type_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public DietType update(@PathVariable("diet_type_id") String id,
                              @Valid @RequestBody DietType dietType) {
        dietType.setId(id);
        return service.update(dietType);
    }

    @DeleteMapping("/api/diet-types/{diet_type_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("diet_type_id") String id) {
        service.delete(id);
    }
}
