package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Meal;

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
    List<Meal> findAllByMealtimeTypeIdAndDietTypeIdAndCaloriesTypeId(@Nonnull Long mealtimeTypeId,
                                                                        @Nonnull Long dietTypeId,
                                                                        @Nonnull Long caloriesTypeId);
}
