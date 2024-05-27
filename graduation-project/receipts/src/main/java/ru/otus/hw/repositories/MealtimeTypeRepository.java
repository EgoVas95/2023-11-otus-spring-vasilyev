package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.MealtimeType;

import java.util.List;
import java.util.Optional;

public interface MealtimeTypeRepository extends JpaRepository<MealtimeType, Long> {
    @Nonnull
    @EntityGraph("receipts-graph")
    @Override
    List<MealtimeType> findAll();

    @Nonnull
    @EntityGraph("receipts-graph")
    @Override
    Optional<MealtimeType> findById(@Nonnull Long id);

    @Nonnull
    @EntityGraph("receipts-graph")
    Optional<MealtimeType> findByName(@Nonnull String name);
}
