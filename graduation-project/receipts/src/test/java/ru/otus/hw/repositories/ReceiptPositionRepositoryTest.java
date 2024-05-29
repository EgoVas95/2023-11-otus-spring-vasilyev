package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.ReceiptPosition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с ингредиентами")
@DataJpaTest
class ReceiptPositionRepositoryTest {
    @Autowired
    private ReceiptPositionRepository repository;

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
        for (ReceiptPosition receiptPosition : getDb()) {
            val findVal  = repository.findById(receiptPosition.getId());
            assertThat(findVal).isPresent().get()
                    .usingRecursiveComparison()
                    .isEqualTo(receiptPosition);
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
        val added = new ReceiptPosition(null, ingredient.getReceipt(),
                ingredient.getServing(), new BigDecimal(55));

        em.merge(added);
        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();
        val find = em.find(ReceiptPosition.class, added.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(added);
    }

    @DisplayName("Изменение порции")
    @Test
    void update() {
        val curr = em.find(ReceiptPosition.class, FIRST_ID);
        val updated = new ReceiptPosition(null, curr.getReceipt(),
                curr.getServing(), new BigDecimal(55));

        assertThat(curr).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(serving -> serving.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(ReceiptPosition.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private Map<Long, List<ReceiptPosition>> getDbByReceipts() {
        Map<Long, List<ReceiptPosition>> map = new HashMap<>();
        getDb().forEach(receiptPosition -> {
            Long id = receiptPosition.getReceipt().getId();
            if (!map.containsKey(id)) {
                map.put(id, new ArrayList<>());
            }
            map.get(id).add(receiptPosition);
        });
        return map;
    }

    private List<ReceiptPosition> getDb() {
        return IntStream.range(1, 8).boxed()
                .map(id -> em.find(ReceiptPosition.class, id))
                .toList();
    }
}
