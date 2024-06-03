package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Mealtime;

import java.util.List;

public interface MealtimeRepository extends MongoRepository<Mealtime, String> {
    List<Mealtime> findAll();
}
