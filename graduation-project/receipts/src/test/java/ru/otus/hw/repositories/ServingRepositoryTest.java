package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Serving;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с порциями")
@DataJpaTest
class ServingRepositoryTest {
    @Autowired
    private ServingRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    private static final BigDecimal[] CALORIES = {
            new BigDecimal(5),
            new BigDecimal(67)
    };

    @DisplayName("Поиск всех порций")
    @Test
    void findAll() {
        val expected = getDb();
        val repVal = repository.findAll();

        assertThat(repVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("Поиск порции по id")
    @Test
    void findById() {
        val expected = getDb();
        for (Serving serving : expected) {
            val repVal = repository.findById(serving.getId());
            assertThat(repVal).isPresent().get()
                    .usingRecursiveComparison()
                    .isEqualTo(serving);
        }
    }

    @DisplayName("Поиск порций по имени")
    @Test
    void findByName() {
        val map = getServingWithNames();
        for (String key : map.keySet()) {
            val repVal = repository.findAllByName(key);
            assertThat(repVal).usingRecursiveComparison()
                    .isEqualTo(map.get(key));
        }
    }

    @DisplayName("Поиск порций по id продукта")
    @Test
    void findByFoodId() {
        val map = getServingsByFoodId();
        for (Long key : map.keySet()) {
            val repVal = repository.findAllByFoodId(key);
            assertThat(repVal).usingRecursiveComparison()
                    .isEqualTo(map.get(key));
        }
    }

    @DisplayName("Поиск порций с калорийностью меньше или равной")
    @Test
    void findByCaloriesLessThan() {
        for(BigDecimal calories : CALORIES) {
            val expected = getDbByCaloriesLessThan(calories);
            val repVal = repository.findAllByCaloriesIsLessThanEqual(calories);

            assertThat(repVal).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @DisplayName("Сохранение порции")
    @Test
    void create() {
        val food = getDb().get(0).getFood();
        val bd = new BigDecimal(1);
        val added = new Serving(null, "Test", food, bd, bd, bd, bd);

        em.merge(added);
        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();
        val find = em.find(Serving.class, added.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(added);
    }

    @DisplayName("Изменение порции")
    @Test
    void update() {
        val curr = em.find(Serving.class, FIRST_ID);
        val updated = new Serving(curr.getId(), "New name", curr.getFood(), curr.getCalories(),
                curr.getProteins(), curr.getFats(), curr.getCarbohydrates());

        assertThat(curr).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(serving -> serving.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(Serving.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private List<Serving> getDbByCaloriesLessThan(BigDecimal calories) {
        List<Serving> result = new ArrayList<>();
        getDb().forEach(serving -> {
            if (serving.getCalories().compareTo(calories) <= 0) {
                result.add(serving);
            }
        });
        return result;
    }

    private Map<Long, List<Serving>> getServingsByFoodId() {
        Map<Long, List<Serving>> result = new HashMap<>();
        getDb().forEach(serving -> {
            Long foodId = serving.getFood().getId();
            if (!result.containsKey(foodId)) {
                result.put(foodId, new ArrayList<>());
            }
            result.get(foodId).add(serving);
        });
        return result;
    }

    private Map<String, List<Serving>> getServingWithNames() {
        Map<String, List<Serving>> result = new HashMap<>();
        getDb().forEach(serving -> {
            String name = serving.getName();
            if (!result.containsKey(name)) {
                result.put(name, new ArrayList<>());
            }
            result.get(name).add(serving);
        });
        return result;
    }

    private List<Serving> getDb() {
        return IntStream.range(1, 10).boxed()
                .map(id -> em.find(Serving.class, id))
                .toList();
    }
}
