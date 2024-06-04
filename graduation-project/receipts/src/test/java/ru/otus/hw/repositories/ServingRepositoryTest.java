package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Food;
import ru.otus.hw.models.Serving;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Типы порций")
@DataMongoTest
public class ServingRepositoryTest {

    private static final String FIRST_ID = "1";

    @Autowired
    private ServingRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void initialize() {
        mongoTemplate.save(new Serving(FIRST_ID, "111L", new Food(FIRST_ID, FIRST_ID), 1L));
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
        val obj = new Serving(null, "111L", new Food(FIRST_ID, FIRST_ID), 1L);
        repository.save(obj);
        assertThat(obj.getId()).isNotBlank();

        val find = mongoTemplate.findById(obj.getId(), Serving.class);
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(obj);
    }

    @DisplayName("update")
    @Test
    public void update() {
        val obj = mongoTemplate.findById(FIRST_ID, Serving.class);
        val expect = new Serving(obj.getId(), obj.getName() + 1,
                new Food(FIRST_ID, FIRST_ID), 1L);

        assertThat(obj).usingRecursiveComparison()
                .isNotEqualTo(expect);

        val ret = repository.save(expect);
        assertThat(ret).isNotNull()
                .matches(r -> !ret.getId().isEmpty())
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expect);

        val find = mongoTemplate.findById(expect.getId(), Serving.class);
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(ret);
    }

    public List<Serving> getDb() {
        return mongoTemplate.findAll(Serving.class);
    }
}
