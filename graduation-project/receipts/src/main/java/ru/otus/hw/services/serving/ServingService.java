package ru.otus.hw.services.serving;

import ru.otus.hw.models.Serving;

import java.util.List;

public interface ServingService {
    Serving findById(String id);

    List<Serving> findAll();

    Serving create(Serving serving);

    Serving update(Serving serving);

    void delete(String id);
}
