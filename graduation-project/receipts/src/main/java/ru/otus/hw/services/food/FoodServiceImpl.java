package ru.otus.hw.services.food;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.food.FoodCreateDto;
import ru.otus.hw.dto.food.FoodDto;
import ru.otus.hw.dto.food.FoodUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.FoodMapper;
import ru.otus.hw.repositories.FoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository repository;

    private final FoodMapper mapper;

    @Override
    public List<FoodDto> findByName(String name) {
        return repository.findAllByName(name)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FoodDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с id = %d не найден!"
                        .formatted(id))));
    }

    @Override
    public List<FoodDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FoodDto create(FoodCreateDto dto) {
        var food = mapper.toModel(dto);
        return mapper.toDto(repository.save(food));
    }

    @Override
    @Transactional
    public FoodDto update(FoodUpdateDto dto) {
        var food = mapper.toModel(dto);
        return mapper.toDto(repository.save(food));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
