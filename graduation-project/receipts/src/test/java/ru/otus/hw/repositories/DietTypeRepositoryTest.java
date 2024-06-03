package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.DietType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Типы диет")
@DataMongoTest
public class DietTypeRepositoryTest {

    private static final String FIRST_ID = "1";

    @Autowired
    private DietTypeRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void initialize() {
        mongoTemplate.save(new DietType(FIRST_ID, "111L"));
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
        val obj = new DietType(null, "1L");
        repository.save(obj);
        assertThat(obj.getId()).isNotBlank();

        val find = mongoTemplate.findById(obj.getId(), DietType.class);
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(obj);
    }

    @DisplayName("update")
    @Test
    public void update() {
        val obj = mongoTemplate.findById(FIRST_ID, DietType.class);
        val expect = new DietType(obj.getId(), obj.getName() + 1);

        assertThat(obj).usingRecursiveComparison()
                .isNotEqualTo(expect);

        val ret = repository.save(expect);
        assertThat(ret).isNotNull()
                .matches(r -> !ret.getId().isEmpty())
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expect);

        val find = mongoTemplate.findById(expect.getId(), DietType.class);
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(ret);
    }
    public List<DietType> getDb() {
        return mongoTemplate.findAll(DietType.class);
    }
}
