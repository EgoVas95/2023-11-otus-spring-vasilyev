package ru.otus.hw.services.mealtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.mealtime.MealtimeTypeCreateDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.MealtimeTypeMapper;
import ru.otus.hw.repositories.MealtimeTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MealtimeTypeServiceImpl implements MealtimeTypeService {

    private final MealtimeTypeMapper mapper;

    private final MealtimeTypeRepository repository;

    @Override
    public List<MealtimeTypeDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MealtimeTypeDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Тип приёма пищи с id = %d не найден!".formatted(id))));
    }

    @Override
    public MealtimeTypeDto findByName(String name) {
        return mapper.toDto(repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Тип приёма пищи с названием = %s не найден!".formatted(name))));
    }

    @Override
    @Transactional
    public MealtimeTypeDto create(MealtimeTypeCreateDto dto) {
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    @Override
    @Transactional
    public MealtimeTypeDto update(MealtimeTypeUpdateDto dto) {
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
