package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Serving;

import java.util.List;

public interface ServingRepository extends MongoRepository<Serving, String> {
    List<Serving> findAll();
}
