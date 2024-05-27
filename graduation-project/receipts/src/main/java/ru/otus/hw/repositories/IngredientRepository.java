package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Nonnull
    @EntityGraph("ingredient-graph")
    @Override
    List<Ingredient> findAll();

    @Nonnull
    @EntityGraph("ingredient-graph")
    @Override
    Optional<Ingredient> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("ingredient-graph")
    List<Ingredient> findAllByReceiptId(@Nonnull Long id);
}
