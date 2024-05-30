package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.diets.DietTypeCreateDto;
import ru.otus.hw.dto.diets.DietTypeDto;
import ru.otus.hw.dto.diets.DietTypeUpdateDto;
import ru.otus.hw.models.DietType;

@Component
public class DietTypeMapper {
    public DietType toModel(DietTypeDto dto) {
        return new DietType(dto.getId(), dto.getName());
    }

    public DietType toModel(DietTypeCreateDto dto) {
        return new DietType(dto.getId(), dto.getName());
    }

    public DietType toModel(DietTypeUpdateDto dto) {
        return new DietType(dto.getId(), dto.getName());
    }

    public DietTypeDto toDto(DietType model) {
        return new DietTypeDto(model.getId(), model.getName());
    }
}
