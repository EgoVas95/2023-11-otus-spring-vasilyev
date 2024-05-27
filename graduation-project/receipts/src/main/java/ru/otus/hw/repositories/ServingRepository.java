package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Serving;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ServingRepository extends JpaRepository<Serving, Long> {

    @Nonnull
    @EntityGraph("servings-graph")
    @Override
    List<Serving> findAll();

    @Nonnull
    @EntityGraph("servings-graph")
    @Override
    Optional<Serving> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("servings-graph")
    List<Serving>findAllByName(@Nonnull String name);

    @Nonnull
    @EntityGraph("servings-graph")
    List<Serving> findAllByFoodId(@Nonnull Long id);

    @Nonnull
    @EntityGraph("servings-graph")
    List<Serving> findAllByCaloriesIsLessThanEqual(@Nonnull BigDecimal calories);
}
