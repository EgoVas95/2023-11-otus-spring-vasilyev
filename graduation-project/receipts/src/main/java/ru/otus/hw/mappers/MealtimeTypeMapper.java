package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.mealtime.MealtimeTypeCreateDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeUpdateDto;
import ru.otus.hw.models.MealtimeType;

@Component
public class MealtimeTypeMapper {
    public MealtimeType toModel(MealtimeTypeDto dto) {
        return new MealtimeType(dto.getId(), dto.getName());
    }

    public MealtimeType toModel(MealtimeTypeCreateDto dto) {
        return new MealtimeType(null, dto.getName());
    }

    public MealtimeType toModel(MealtimeTypeUpdateDto dto) {
        return new MealtimeType(dto.getId(), dto.getName());
    }

    public MealtimeTypeDto toDto(MealtimeType model) {
        return new MealtimeTypeDto(model.getId(), model.getName());
    }
}
