package ru.otus.hw.mealconfigurator.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.mealconfigurator.model.MealList;

public interface MealListRepository extends MongoRepository<MealList, String> {
    MealList findFirstByUserSub(String userSub);
}
