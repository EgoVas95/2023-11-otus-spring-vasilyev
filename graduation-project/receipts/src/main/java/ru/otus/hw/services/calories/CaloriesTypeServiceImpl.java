package ru.otus.hw.services.calories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.CaloriesType;
import ru.otus.hw.repositories.CaloriesTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaloriesTypeServiceImpl implements CaloriesTypeService {

    private final CaloriesTypeRepository repository;

    @Override
    public CaloriesType findById(String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<CaloriesType> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public CaloriesType create(CaloriesType caloriesType) {
        return repository.save(caloriesType);
    }

    @Override
    @Transactional
    public CaloriesType update(CaloriesType caloriesType) {
        repository.findById(caloriesType.getId()).orElseThrow(EntityNotFoundException::new);
        return repository.save(caloriesType);
    }

    @Override
    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}
