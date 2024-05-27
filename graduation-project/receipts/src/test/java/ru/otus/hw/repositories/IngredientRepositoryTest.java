package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Ingredient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с ингредиентами")
@DataJpaTest
class IngredientRepositoryTest {
    @Autowired
    private IngredientRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск всех ингредиентов")
    void findAll() {
        val expect = getDb();
        val repVal = repository.findAll();

        assertThat(repVal).usingRecursiveComparison()
                .isEqualTo(expect);
    }

    @Test
    @DisplayName("Поиск ингредиента по id")
    void findById() {
        for (Ingredient ingredient : getDb()) {
            val findVal  = repository.findById(ingredient.getId());
            assertThat(findVal).isPresent().get()
                    .usingRecursiveComparison()
                    .isEqualTo(ingredient);
        }
    }

    @Test
    @DisplayName("Поиск ингредиента по id рецепта")
    void findByReceiptId() {
        val map = getDbByReceipts();
        for (Long key : map.keySet()) {
           val repVal = repository.findAllByReceiptId(key);
           assertThat(repVal).usingRecursiveComparison()
                   .isEqualTo(map.get(key));
        }
    }

    @DisplayName("Сохранение игредиента")
    @Test
    void create() {
        val ingredient = getDb().get(0);
        val added = new Ingredient(null, ingredient.getReceipt(),
                ingredient.getServing(), new BigDecimal(55));

        em.merge(added);
        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();
        val find = em.find(Ingredient.class, added.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(added);
    }

    @DisplayName("Изменение порции")
    @Test
    void update() {
        val curr = em.find(Ingredient.class, FIRST_ID);
        val updated = new Ingredient(null, curr.getReceipt(),
                curr.getServing(), new BigDecimal(55));

        assertThat(curr).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(serving -> serving.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(Ingredient.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private Map<Long, List<Ingredient>> getDbByReceipts() {
        Map<Long, List<Ingredient>> map = new HashMap<>();
        getDb().forEach(ingredient -> {
            Long id = ingredient.getReceipt().getId();
            if (!map.containsKey(id)) {
                map.put(id, new ArrayList<>());
            }
            map.get(id).add(ingredient);
        });
        return map;
    }

    private List<Ingredient> getDb() {
        return IntStream.range(1, 8).boxed()
                .map(id -> em.find(Ingredient.class, id))
                .toList();
    }
}
