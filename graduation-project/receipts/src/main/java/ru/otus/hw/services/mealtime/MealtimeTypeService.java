package ru.otus.hw.services.mealtime;

import ru.otus.hw.dto.mealtime.MealtimeTypeCreateDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeUpdateDto;

import java.util.List;

public interface MealtimeTypeService {
    List<MealtimeTypeDto> findAll();

    MealtimeTypeDto findById(Long id);

    MealtimeTypeDto findByName(String name);

    MealtimeTypeDto create(MealtimeTypeCreateDto dto);

    MealtimeTypeDto update(MealtimeTypeUpdateDto dto);

    void delete(Long id);
}
