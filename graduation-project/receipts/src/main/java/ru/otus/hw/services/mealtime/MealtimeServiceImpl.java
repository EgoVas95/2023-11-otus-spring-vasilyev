package ru.otus.hw.services.mealtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Mealtime;
import ru.otus.hw.repositories.MealtimeRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MealtimeServiceImpl implements MealtimeService {
    private final MealtimeRepository repository;

    @Override
    public Mealtime findById(String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Mealtime> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Mealtime create(Mealtime mealtime) {
        return repository.save(mealtime);
    }

    @Override
    @Transactional
    public Mealtime update(Mealtime mealtime) {
        repository.findById(mealtime.getId()).orElseThrow(EntityNotFoundException::new);
        return repository.save(mealtime);
    }

    @Override
    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}
