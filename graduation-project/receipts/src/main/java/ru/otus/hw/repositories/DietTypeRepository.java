package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.DietType;

import java.util.List;

public interface DietTypeRepository extends MongoRepository<DietType, String> {
    List<DietType> findAll();
}
