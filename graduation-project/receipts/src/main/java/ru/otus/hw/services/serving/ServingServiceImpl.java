package ru.otus.hw.services.serving;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.serving.ServingCreateDto;
import ru.otus.hw.dto.serving.ServingDto;
import ru.otus.hw.dto.serving.ServingUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.ServingMapper;
import ru.otus.hw.repositories.FoodRepository;
import ru.otus.hw.repositories.ServingRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ServingServiceImpl implements ServingService {

    private final ServingMapper servingMapper;


    private final ServingRepository servingRepository;

    private final FoodRepository foodRepository;

    @Override
    public List<ServingDto> findAll() {
        return servingRepository.findAll()
                .stream().map(servingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServingDto findById(Long id) {
        return servingMapper.toDto(servingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Порция с id = %d не найдена!".formatted(id))));
    }

    @Override
    public List<ServingDto> findByFoodId(Long id) {
        return servingRepository.findAllByFoodId(id)
                .stream().map(servingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServingDto> findByName(String name) {
        return servingRepository.findAllByName(name)
                .stream().map(servingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServingDto> findByCaloriesLessOrEqThan(BigDecimal calories) {
        return servingRepository.findAllByCaloriesIsLessThanEqual(calories)
                .stream().map(servingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ServingDto create(ServingCreateDto createDto) {
        var foodId = createDto.getFoodId();
        var food = foodRepository.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Продукт с id = %d не найден!".formatted(foodId)));
        return servingMapper.toDto(servingRepository.save(
                servingMapper.toModel(createDto, food)));
    }

    @Override
    @Transactional
    public ServingDto update(ServingUpdateDto updateDto) {
        var foodId = updateDto.getFoodId();
        var food = foodRepository.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Продукт с id = %d не найден!".formatted(foodId)));
        return servingMapper.toDto(servingRepository.save(
                servingMapper.toModel(updateDto, food)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        servingRepository.deleteById(id);
    }
}
