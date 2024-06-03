package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.CaloriesType;

import java.util.List;

public interface CaloriesTypeRepository extends MongoRepository<CaloriesType, String> {
    List<CaloriesType> findAll();
}
