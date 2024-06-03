package ru.otus.hw.services.receipt;

import ru.otus.hw.models.Receipt;
import java.util.List;

public interface ReceiptService {
    Receipt findById(String id);

    List<Receipt> findAll();

    List<Receipt> findAllByMealtimeIdAndDietTypeIdAndAndCaloriesTypeId(String mealTimeId,
                                                                       String dietTypeId,
                                                                       String caloriesTypeId);

    Receipt create(Receipt receipt);

    Receipt update(Receipt receipt);

    void delete(String id);
}
