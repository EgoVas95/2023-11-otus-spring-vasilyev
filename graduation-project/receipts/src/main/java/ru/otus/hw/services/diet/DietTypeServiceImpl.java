package ru.otus.hw.services.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.diets.DietTypeCreateDto;
import ru.otus.hw.dto.diets.DietTypeDto;
import ru.otus.hw.dto.diets.DietTypeUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.DietTypeMapper;
import ru.otus.hw.repositories.DietTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DietTypeServiceImpl implements DietTypeService {
    private final DietTypeRepository repository;

    private final DietTypeMapper mapper;

    @Override
    public DietTypeDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Диета с id = %d не найден!".formatted(id))));
    }

    @Override
    public List<DietTypeDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DietTypeDto create(DietTypeCreateDto dto) {
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    @Override
    public DietTypeDto update(DietTypeUpdateDto dto) {
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
