package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Food;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    @Nonnull
    List<Food> findAllByName(@Nonnull String name);
}
