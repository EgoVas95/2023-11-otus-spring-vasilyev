package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Food;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food, String> {
    List<Food> findAll();
}
