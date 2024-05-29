package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Meal;
import ru.otus.hw.models.MealPosition;

import java.util.List;
import java.util.Optional;

public interface MealPositionRepository extends JpaRepository<MealPosition, Long> {
    @Nonnull
    @EntityGraph("meal-graph")
    @Override
    List<MealPosition> findAll();

    @Nonnull
    @EntityGraph("meal-graph")
    @Override
    Optional<MealPosition> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("meal-graph")
    List<MealPosition> findAllByMeal(@Nonnull Meal meal);
}
