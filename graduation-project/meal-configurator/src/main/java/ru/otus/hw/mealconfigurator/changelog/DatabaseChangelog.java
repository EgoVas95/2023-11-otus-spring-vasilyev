package ru.otus.hw.mealconfigurator.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.mealconfigurator.model.MealList;
import ru.otus.hw.mealconfigurator.model.PriceList;
import ru.otus.hw.mealconfigurator.repositories.MealListRepository;
import ru.otus.hw.mealconfigurator.repositories.PriceListRepository;

import java.util.Collections;

@ChangeLog
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "dropTables", author = "egovas", runAlways = true)
    public void dropTables(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "addMealList", author = "egovas")
    public void addMealList(MealListRepository repository) {
        repository.save(new MealList("1", "1", Collections.emptyList()));
    }

    @ChangeSet(order = "003", id = "addPriceList", author = "egovas")
    public void addPriceList(PriceListRepository repository) {
        repository.save(new PriceList("1", "1", Collections.emptyList()));
    }
}
