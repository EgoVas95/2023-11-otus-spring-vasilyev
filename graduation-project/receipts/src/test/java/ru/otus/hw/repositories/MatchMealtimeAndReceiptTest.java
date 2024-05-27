package ru.otus.hw.repositories;


import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.MatchMealtimeAndReceipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с сопоставлением рецепта и типа приёма пищи")
@DataJpaTest
class MatchMealtimeAndReceiptTest {
    @Autowired
    private MatchMealtimeAndReceiptRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск совпадения по id")
    void findById() {
        val expected = getDb();
        for (MatchMealtimeAndReceipt obj : expected) {
            val repositoryById = repository.findById(obj.getId());
            assertThat(repositoryById).isPresent().get()
                    .usingRecursiveComparison().isEqualTo(obj);
        }
    }

    @Test
    @DisplayName("Поиск полного списка совпадений")
    void findAll() {
        val expected = getDb();
        val repositoryVal = repository.findAll();

        assertThat(repositoryVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Поиск совпадения по id рецепта")
    void findByReceiptId() {
        val map = getByReceipts();
        for (Long key : map.keySet()) {
            val repVal = repository.findAllByReceiptId(key);
            assertThat(repVal).usingRecursiveComparison()
                    .isEqualTo(map.get(key));
        }
    }

    @Test
    @DisplayName("Поиск совпадения по id типа приёма пищи")
    void findByMealtimeId() {
        val map = getByMealtime();
        for (Long key : map.keySet()) {
            val repVal = repository.findAllByMealtimeTypeId(key);
            assertThat(repVal).usingRecursiveComparison()
                    .isEqualTo(map.get(key));
        }
    }

    @Test
    @DisplayName("Сохранение совпадения")
    void create() {
        val match = getDb().get(0);
        val added = new MatchMealtimeAndReceipt(null, match.getReceipt(),
                match.getMealtimeType());
        em.merge(added);

        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();

        val find = em.find(MatchMealtimeAndReceipt.class, added.getId());
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(added);
    }

    @Test
    @DisplayName("Изменение совпадения")
    void update() {
        val current = em.find(MatchMealtimeAndReceipt.class, FIRST_ID);
        val updated = new MatchMealtimeAndReceipt(current.getId(),
                current.getReceipt(), null);

        assertThat(current).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(food -> food.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(MatchMealtimeAndReceipt.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private Map<Long, List<MatchMealtimeAndReceipt>> getByMealtime() {
        Map<Long, List<MatchMealtimeAndReceipt>> result = new HashMap<>();
        getDb().forEach(match -> {
            Long id = match.getMealtimeType().getId();
            if(!result.containsKey(id)) {
                result.put(id, new ArrayList<>());
            }
            result.get(id).add(match);
        });
        return result;
    }

    private Map<Long, List<MatchMealtimeAndReceipt>> getByReceipts() {
        Map<Long, List<MatchMealtimeAndReceipt>> result = new HashMap<>();
        getDb().forEach(match -> {
            Long id = match.getReceipt().getId();
            if(!result.containsKey(id)) {
                result.put(id, new ArrayList<>());
            }
            result.get(id).add(match);
        });
        return result;
    }

    private List<MatchMealtimeAndReceipt> getDb() {
        return IntStream.range(1, 5).boxed()
                .map(id -> em.find(MatchMealtimeAndReceipt.class, id))
                .toList();
    }
}
