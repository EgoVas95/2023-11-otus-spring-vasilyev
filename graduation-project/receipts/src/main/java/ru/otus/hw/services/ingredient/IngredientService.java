package ru.otus.hw.services.ingredient;

import ru.otus.hw.dto.ingredient.IngredientCreateDto;
import ru.otus.hw.dto.ingredient.IngredientDto;
import ru.otus.hw.dto.ingredient.IngredientUpdateDto;

import java.util.List;

public interface IngredientService {
    List<IngredientDto> findAll();

    IngredientDto findById(Long id);

    List<IngredientDto> findByReceiptId(Long id);

    IngredientDto create(IngredientCreateDto dto);

    IngredientDto update(IngredientUpdateDto dto);

    void delete(Long id);
}
