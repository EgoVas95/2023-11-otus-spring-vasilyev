package ru.otus.hw.mealconfigurator.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.mealconfigurator.model.Day;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayRepository extends MongoRepository<Day, String> {
    List<Day> findAllByUserId(String userId);

    Optional<Day> findFirstByUserIdAndDate(String userId, LocalDate date);

    void deleteAllByUserId(String userId);
}
