package ru.otus.hw.services.meal_position;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.meal_positions.MealPositionCreateDto;
import ru.otus.hw.dto.meal_positions.MealPositionDto;
import ru.otus.hw.dto.meal_positions.MealPositionUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.MealMapper;
import ru.otus.hw.mappers.MealPositionMapper;
import ru.otus.hw.repositories.MealPositionRepository;
import ru.otus.hw.repositories.MealRepository;
import ru.otus.hw.repositories.ServingRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MealPositionServiceImpl implements MealPositionService {

    private final MealPositionMapper mapper;

    private final MealMapper mealMapper;

    private final MealPositionRepository repository;

    private final MealRepository mealRepository;

    private final ServingRepository servingRepository;

    @Override
    public List<MealPositionDto> findAllByMeal(MealDto mealDto) {
        return repository.findAllByMeal(mealMapper.toModel(mealDto))
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealPositionDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MealPositionDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Позиция приёма пищи с id = %d не найден!".formatted(id))));
    }

    @Override
    @Transactional
    public MealPositionDto create(MealPositionCreateDto dto) {
        var mealId = dto.getMeal().getId();
        var meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Позиция приёма пищи с id = %d не найден!".formatted(mealId)));

        var servingId = dto.getServing().getId();
        var serving = servingRepository.findById(servingId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Порция с id = %d не найден!".formatted(mealId)));

        return mapper.toDto(repository.save(mapper.toModel(dto,
                meal, serving)));
    }

    @Override
    @Transactional
    public MealPositionDto update(MealPositionUpdateDto dto) {
        var mealId = dto.getMeal().getId();
        var meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Позиция приёма пищи с id = %d не найден!".formatted(mealId)));

        var servingId = dto.getServing().getId();
        var serving = servingRepository.findById(servingId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Порция с id = %d не найден!".formatted(mealId)));

        return mapper.toDto(repository.save(mapper.toModel(dto,
                meal, serving)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
