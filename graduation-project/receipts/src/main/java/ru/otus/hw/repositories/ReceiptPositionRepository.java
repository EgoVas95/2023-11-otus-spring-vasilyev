package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.ReceiptPosition;

import java.util.List;
import java.util.Optional;

public interface ReceiptPositionRepository extends JpaRepository<ReceiptPosition, Long> {

    @Nonnull
    @EntityGraph("receipt-position-graph")
    @Override
    List<ReceiptPosition> findAll();

    @Nonnull
    @EntityGraph("receipt-position-graph")
    @Override
    Optional<ReceiptPosition> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("receipt-position-graph")
    List<ReceiptPosition> findAllByReceiptId(@Nonnull Long id);
}
