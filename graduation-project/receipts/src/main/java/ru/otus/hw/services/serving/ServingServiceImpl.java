package ru.otus.hw.services.serving;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Serving;
import ru.otus.hw.repositories.ServingRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServingServiceImpl implements ServingService {

    private final ServingRepository repository;

    @Override
    public Serving findById(String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Serving> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Serving create(Serving serving) {
        return repository.save(serving);
    }

    @Override
    @Transactional
    public Serving update(Serving serving) {
        repository.findById(serving.getId()).orElseThrow(EntityNotFoundException::new);
        return repository.save(serving);
    }

    @Override
    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}
