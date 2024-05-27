package ru.otus.hw.services.serving;

import ru.otus.hw.dto.serving.ServingCreateDto;
import ru.otus.hw.dto.serving.ServingDto;
import ru.otus.hw.dto.serving.ServingUpdateDto;

import java.math.BigDecimal;
import java.util.List;

public interface ServingService {
    List<ServingDto> findAll();

    ServingDto findById(Long id);

    List<ServingDto> findByFoodId(Long id);

    List<ServingDto> findByName(String name);

    List<ServingDto> findByCaloriesLessOrEqThan(BigDecimal calories);

    ServingDto create(ServingCreateDto createDto);

    ServingDto update(ServingUpdateDto updateDto);

    void delete(Long id);
}
