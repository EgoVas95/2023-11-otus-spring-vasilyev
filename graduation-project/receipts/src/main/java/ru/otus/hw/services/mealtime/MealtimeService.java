package ru.otus.hw.services.mealtime;

import ru.otus.hw.models.Mealtime;

import java.util.List;

public interface MealtimeService {
    Mealtime findById(String id);

    List<Mealtime> findAll();

    Mealtime create(Mealtime mealtime);

    Mealtime update(Mealtime mealtime);

    void delete(String id);
}
