package ru.otus.hw.services.diet;


import ru.otus.hw.models.DietType;

import java.util.List;

public interface DietTypeService {
    DietType findById(String id);

    List<DietType> findAll();

    DietType create(DietType dietType);

    DietType update(DietType dietType);

    void delete(String id);
}
