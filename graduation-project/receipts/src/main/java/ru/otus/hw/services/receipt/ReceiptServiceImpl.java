package ru.otus.hw.services.receipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Receipt;
import ru.otus.hw.repositories.ReceiptRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository repository;

    @Override
    public Receipt findById(String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Receipt> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Receipt> findAllByMealtimeIdAndDietTypeIdAndAndCaloriesTypeId(String mealTimeId,
                                                                              String dietTypeId,
                                                                              String caloriesTypeId) {
        return repository.findAllByMealtimeIdAndDietTypeIdAndAndCaloriesTypeId(mealTimeId,
                dietTypeId, caloriesTypeId);
    }

    @Override
    @Transactional
    public Receipt create(Receipt receipt) {
        return repository.save(receipt);
    }

    @Override
    @Transactional
    public Receipt update(Receipt receipt) {
        repository.findById(receipt.getId())
                .orElseThrow(EntityNotFoundException::new);
        return repository.save(receipt);
    }

    @Override
    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}
