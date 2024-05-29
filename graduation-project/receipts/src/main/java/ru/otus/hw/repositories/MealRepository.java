package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.CaloriesType;
import ru.otus.hw.models.DietType;
import ru.otus.hw.models.Meal;
import ru.otus.hw.models.MealtimeType;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    @Nonnull
    @EntityGraph("meal-graph")
    @Override
    List<Meal> findAll();

    @Nonnull
    @EntityGraph("meal-graph")
    @Override
    Optional<Meal> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("meal-graph")
    List<Meal> findAllByMealtimeTypeAndDietTypeAndAndCaloriesType(
            MealtimeType mealtimeType, DietType dietType, CaloriesType caloriesType);
}
