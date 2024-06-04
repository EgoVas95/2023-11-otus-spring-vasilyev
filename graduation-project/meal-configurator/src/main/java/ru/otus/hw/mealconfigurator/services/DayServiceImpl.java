package ru.otus.hw.mealconfigurator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.mealconfigurator.exceptions.EntityNotFoundException;
import ru.otus.hw.mealconfigurator.model.Day;
import ru.otus.hw.mealconfigurator.repositories.DayRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {
    private final DayRepository repository;

    @Override
    public Day findById(String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Day> findAllByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Day findFirstByUserIdAndDate(String userId, LocalDate date) {
        return repository.findFirstByUserIdAndDate(userId, date)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteAllByUserId(String userId) {
        repository.deleteAllByUserId(userId);
    }

    @Override
    public Day create(Day day) {
        return repository.save(day);
    }

    @Override
    public Day update(Day day) {
        repository.findById(day.getId())
                .orElseThrow(EntityNotFoundException::new);
        return repository.save(day);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
