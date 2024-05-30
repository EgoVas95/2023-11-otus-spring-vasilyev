package ru.otus.hw.services.meal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.meal.MealCreateDto;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.meal.MealUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.MealMapper;
import ru.otus.hw.repositories.CaloriesTypeRepository;
import ru.otus.hw.repositories.DietTypeRepository;
import ru.otus.hw.repositories.MealRepository;
import ru.otus.hw.repositories.MealtimeTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MealServiceImpl implements MealService {
    private final MealRepository repository;

    private final MealtimeTypeRepository mealtimeTypeRepository;

    private final DietTypeRepository dietTypeRepository;

    private final CaloriesTypeRepository caloriesTypeRepository;


    private final MealMapper mapper;

    @Override
    public List<MealDto> findAllBySeveralParams(Long mealtimeTypeId,
                                                Long dietTypeId,
                                                Long caloriesTypeId) {
        return repository.findAllByMealtimeTypeIdAndDietTypeIdAndCaloriesTypeId(
                        mealtimeTypeId, dietTypeId, caloriesTypeId)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MealDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Диета с id = %d не найден!".formatted(id))));
    }

    @Override
    public List<MealDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MealDto create(MealCreateDto dto) {
        var mealtimeId = dto.getMealtimeTypeDto().getId();
        var mealtime = mealtimeTypeRepository.findById(mealtimeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Тип приёма пищи с id = %d не найден!".formatted(mealtimeId)));
        var diet = dietTypeRepository.findById(dto.getDietTypeDto().getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Тип диеты с id = %d не найден!".formatted(mealtimeId)));
        var calories = caloriesTypeRepository.findById(dto.getCaloriesDto().getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Тип калорий с id = %d не найден!".formatted(mealtimeId)));
        return mapper.toDto(repository.save(mapper.toModel(mealtime, diet, calories)));
    }

    @Override
    public MealDto update(MealUpdateDto dto) {
        var mealtimeId = dto.getMealtimeTypeDto().getId();
        var mealtime = mealtimeTypeRepository.findById(mealtimeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Тип приёма пищи с id = %d не найден!".formatted(mealtimeId)));
        var diet = dietTypeRepository.findById(dto.getDietTypeDto().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Тип диеты с id = %d не найден!".formatted(mealtimeId)));
        var calories = caloriesTypeRepository.findById(dto.getCaloriesDto().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Тип калорий с id = %d не найден!".formatted(mealtimeId)));

        return mapper.toDto(repository.save(mapper.toModel(mealtime, diet, calories)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
