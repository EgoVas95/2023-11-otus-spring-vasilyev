package ru.otus.hw.services.calories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.calories.CaloriesTypeCreateDto;
import ru.otus.hw.dto.calories.CaloriesTypeDto;
import ru.otus.hw.dto.calories.CaloriesTypeUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.CaloriesTypeMapper;
import ru.otus.hw.repositories.CaloriesTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CaloriesServiceImpl implements CaloriesService {
    private final CaloriesTypeRepository repository;

    private final CaloriesTypeMapper mapper;

    @Override
    public CaloriesTypeDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Диета с id = %d не найден!".formatted(id))));
    }

    @Override
    public List<CaloriesTypeDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CaloriesTypeDto create(CaloriesTypeCreateDto dto) {
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    @Override
    public CaloriesTypeDto update(CaloriesTypeUpdateDto dto) {
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
