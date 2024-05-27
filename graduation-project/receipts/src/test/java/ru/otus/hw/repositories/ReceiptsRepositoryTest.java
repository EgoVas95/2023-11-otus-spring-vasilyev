package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с рецептами")
@DataJpaTest
class ReceiptsRepositoryTest {
    @Autowired
    private ReceiptsRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск рецепта по id")
    void findById() {
        val expected = getDb();
        for (Receipt obj : expected) {
            val repositoryById = repository.findById(obj.getId());
            assertThat(repositoryById).isPresent().get()
                    .usingRecursiveComparison().isEqualTo(obj);
        }
    }

    @Test
    @DisplayName("Поиск полного списка рецептов")
    void findAll() {
        val expected = getDb();
        val repositoryVal = repository.findAll();

        assertThat(repositoryVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Поиск рецепта по id продукта")
    void findByFoodId() {
        val map = getByFood();
        for (Long key : map.keySet()) {
            val repVal = repository.findAllByFoodId(key);
            assertThat(repVal).usingRecursiveComparison()
                    .isEqualTo(map.get(key));
        }
    }

    @Test
    @DisplayName("Сохранение рецепта")
    void create() {
        val match = getDb().get(0);
        val added = new Receipt(null, match.getFood(),
                "new instruction");
        em.merge(added);

        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();

        val find = em.find(Receipt.class, added.getId());
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(added);
    }

    @Test
    @DisplayName("Изменение рецепта")
    void update() {
        val current = em.find(Receipt.class, FIRST_ID);
        val updated = new Receipt(current.getId(),
                current.getFood(), "new instruction");

        assertThat(current).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(food -> food.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(Receipt.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private Map<Long, List<Receipt>> getByFood() {
        Map<Long, List<Receipt>> result = new HashMap<>();
        getDb().forEach(match -> {
            Long id = match.getFood().getId();
            if(!result.containsKey(id)) {
                result.put(id, new ArrayList<>());
            }
            result.get(id).add(match);
        });
        return result;
    }

    private List<Receipt> getDb() {
        return IntStream.range(1, 3).boxed()
                .map(id -> em.find(Receipt.class, id))
                .toList();
    }
}
