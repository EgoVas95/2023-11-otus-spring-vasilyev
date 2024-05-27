package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Receipt;

import java.util.List;
import java.util.Optional;

public interface ReceiptsRepository extends JpaRepository<Receipt, Long> {

    @Nonnull
    @EntityGraph("receipts-graph")
    @Override
    List<Receipt> findAll();

    @Nonnull
    @EntityGraph("receipts-graph")
    @Override
    Optional<Receipt> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("receipts-graph")
    List<Receipt> findAllByFoodId(@Nonnull Long id);
}
