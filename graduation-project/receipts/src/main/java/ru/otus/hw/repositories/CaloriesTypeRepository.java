package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.CaloriesType;

public interface CaloriesTypeRepository extends JpaRepository<CaloriesType, Long> {
}
