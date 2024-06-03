package ru.otus.hw.services.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.DietType;
import ru.otus.hw.repositories.DietTypeRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DietTypeServiceImpl implements DietTypeService {
    private final DietTypeRepository repository;

    @Override
    public DietType findById(String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<DietType> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public DietType create(DietType dietType) {
        return repository.save(dietType);
    }

    @Override
    @Transactional
    public DietType update(DietType dietType) {
        repository.findById(dietType.getId()).orElseThrow(EntityNotFoundException::new);
        return repository.save(dietType);
    }

    @Override
    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}
