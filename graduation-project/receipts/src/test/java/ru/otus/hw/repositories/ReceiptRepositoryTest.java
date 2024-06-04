package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.CaloriesType;
import ru.otus.hw.models.DietType;
import ru.otus.hw.models.Mealtime;
import ru.otus.hw.models.Receipt;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Типы порций")
@DataMongoTest
public class ReceiptRepositoryTest {
    private static final String FIRST_ID = "1";

    @Autowired
    private ReceiptRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void initialize() {
        mongoTemplate.save(new Receipt(FIRST_ID, "111L",
                new CaloriesType(FIRST_ID, 1L),
                new DietType(FIRST_ID, FIRST_ID),
                new Mealtime(FIRST_ID, FIRST_ID),
                Collections.emptyList()));
    }

    @DisplayName("find byId")
    @Test
    void findById() {
        getDb().forEach(expect -> {
            var res = repository.findById(expect.getId());
            assertThat(res).isPresent().get()
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        });
    }

    @DisplayName("save")
    @Test
    public void save() {
        val obj = new Receipt(null, "111L",
                new CaloriesType(FIRST_ID, 1L),
                new DietType(FIRST_ID, FIRST_ID),
                new Mealtime(FIRST_ID, FIRST_ID),
                Collections.emptyList());
        repository.save(obj);
        assertThat(obj.getId()).isNotBlank();

        val find = mongoTemplate.findById(obj.getId(), Receipt.class);
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(obj);
    }

    @DisplayName("update")
    @Test
    public void update() {
        val obj = mongoTemplate.findById(FIRST_ID, Receipt.class);
        val expect = new Receipt(FIRST_ID, "111L" + 1,
                new CaloriesType(FIRST_ID, 1L),
                new DietType(FIRST_ID, FIRST_ID),
                new Mealtime(FIRST_ID, FIRST_ID),
                Collections.emptyList());

        assertThat(obj).usingRecursiveComparison()
                .isNotEqualTo(expect);

        val ret = repository.save(expect);
        assertThat(ret).isNotNull()
                .matches(r -> !ret.getId().isEmpty())
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expect);

        val find = mongoTemplate.findById(expect.getId(), Receipt.class);
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(ret);
    }

    public List<Receipt> getDb() {
        return mongoTemplate.findAll(Receipt.class);
    }
}
