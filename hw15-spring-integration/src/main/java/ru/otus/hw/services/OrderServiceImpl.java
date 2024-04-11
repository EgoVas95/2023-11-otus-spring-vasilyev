package ru.otus.hw.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.BuildOrder;
import ru.otus.hw.domain.FoundationTypes;
import ru.otus.hw.domain.WallsTypes;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService, CommandLineRunner {

    private final BuildServiceGateway build;

    @Override
    public void startGenerateOrdersLoop() {
        try (ForkJoinPool pool = ForkJoinPool.commonPool();
             Closeable ignored = pool::shutdown) {
            for (int i = 0; i < 1; i++) {
                int num = i + 1;
                pool.execute(() -> {
                    Collection<BuildOrder> items = generateOrderItems();
                    log.info("{}, New orderItems: {}", num, items.stream()
                            .map(BuildOrder::toString)
                            .collect(Collectors.joining(", ")));
                    build.building(items);
                });
                delay();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private static BuildOrder generateOrderItem() {
        Random random = new Random();
        FoundationTypes[] foundations = FoundationTypes.values();
        WallsTypes[] wallsTypes = WallsTypes.values();

        return new BuildOrder(foundations[random.nextInt(0, foundations.length)]
                .getTypeName(),
                wallsTypes[random.nextInt(0, wallsTypes.length)]
                        .getTypeName(),
                random.nextInt(1, 20)
        );
    }

    private static Collection<BuildOrder> generateOrderItems() {
        Random random = new Random();
        List<BuildOrder> items = new ArrayList<>();
        for (int idx = 0; idx < random.nextInt(1, 10); idx++) {
            items.add(generateOrderItem());
        }
        return items;
    }

    private void delay() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run(String... args) throws Exception {
        startGenerateOrdersLoop();
    }
}
