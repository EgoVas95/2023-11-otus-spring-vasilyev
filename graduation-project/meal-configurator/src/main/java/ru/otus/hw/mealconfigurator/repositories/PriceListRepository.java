package ru.otus.hw.mealconfigurator.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.mealconfigurator.model.PriceList;

public interface PriceListRepository extends MongoRepository<PriceList, String> {
    PriceList findFirstByUserSub(String userSub);
}
