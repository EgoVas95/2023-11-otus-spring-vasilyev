package ru.otus.hw.services.diet;

import ru.otus.hw.dto.diets.DietTypeCreateDto;
import ru.otus.hw.dto.diets.DietTypeDto;
import ru.otus.hw.dto.diets.DietTypeUpdateDto;

import java.util.List;

public interface DietTypeService {
    DietTypeDto findById(Long id);

    List<DietTypeDto> findAll();

    DietTypeDto create(DietTypeCreateDto dto);

    DietTypeDto update(DietTypeUpdateDto dto);

    void delete(Long id);
}
