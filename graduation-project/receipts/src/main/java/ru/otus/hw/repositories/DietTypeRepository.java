package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.DietType;

public interface DietTypeRepository extends JpaRepository<DietType, Long> {
}
