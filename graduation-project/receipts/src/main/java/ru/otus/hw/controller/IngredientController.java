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
import ru.otus.hw.dto.ingredient.IngredientCreateDto;
import ru.otus.hw.dto.ingredient.IngredientDto;
import ru.otus.hw.dto.ingredient.IngredientUpdateDto;
import ru.otus.hw.services.ingredient.IngredientServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientServiceImpl service;

    @GetMapping("/api/ingredients")
    public List<IngredientDto> getAllIngredients() {
        return service.findAll();
    }

    @GetMapping("/api/ingredients/{ingredient_id}")
    public IngredientDto getIngredientById(@PathVariable("ingredient_id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/api/receipts/ingredients/{receipt_id}")
    public List<IngredientDto> getIngredientByReceiptId(@PathVariable("receipt_id") Long id) {
        return service.findByReceiptId(id);
    }

    @PostMapping("/api/ingredients")
    @ResponseStatus(value = HttpStatus.CREATED)
    public IngredientDto createIngredient(@Valid @RequestBody IngredientCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/ingredients/{ingredient_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public IngredientDto updateIngredient(@PathVariable("ingredient_id") Long id,
                                          @Valid @RequestBody IngredientUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/ingredients/{ingredient_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("ingredient_id") Long id) {
        service.delete(id);
    }
}
