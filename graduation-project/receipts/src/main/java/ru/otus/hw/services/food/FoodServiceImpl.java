package ru.otus.hw.services.food;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Food;
import ru.otus.hw.repositories.FoodRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository repository;

    @Override
    public Food findById(String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Food> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Food create(Food food) {
        return repository.save(food);
    }

    @Override
    @Transactional
    public Food update(Food food) {
        repository.findById(food.getId()).orElseThrow(EntityNotFoundException::new);
        return repository.save(food);
    }

    @Override
    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}
