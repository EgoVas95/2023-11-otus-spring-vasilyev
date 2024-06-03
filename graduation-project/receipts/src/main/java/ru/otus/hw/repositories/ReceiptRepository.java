package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Receipt;

import java.util.List;

public interface ReceiptRepository extends MongoRepository<Receipt, String> {
    List<Receipt> findAllByMealtimeIdAndDietTypeIdAndAndCaloriesTypeId(String mealTimeId,
                                                                       String dietTypeId,
                                                                       String caloriesTypeId);

    List<Receipt> findAll();
}
