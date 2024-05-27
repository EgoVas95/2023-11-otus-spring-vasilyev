package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.MatchMealtimeAndReceipt;

import java.util.List;
import java.util.Optional;

public interface MatchMealtimeAndReceiptRepository extends JpaRepository<MatchMealtimeAndReceipt, Long> {

    @Nonnull
    @EntityGraph("match-meal-to-receipt-graph")
    @Override
    List<MatchMealtimeAndReceipt> findAll();

    @Nonnull
    @EntityGraph("match-meal-to-receipt-graph")
    @Override
    Optional<MatchMealtimeAndReceipt> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("match-meal-to-receipt-graph")
    List<MatchMealtimeAndReceipt> findAllByReceiptId(@Nonnull Long id);

    @Nonnull
    @EntityGraph("match-meal-to-receipt-graph")
    List<MatchMealtimeAndReceipt> findAllByMealtimeTypeId(@Nonnull Long id);
}
