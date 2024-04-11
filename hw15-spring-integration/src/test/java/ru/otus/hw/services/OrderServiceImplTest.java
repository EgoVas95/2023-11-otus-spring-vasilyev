package ru.otus.hw.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.domain.BuildOrder;
import ru.otus.hw.domain.BuildingResult;
import ru.otus.hw.domain.FoundationTypes;
import ru.otus.hw.domain.WallsTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private BuildServiceGateway build;

    @Test
    void startGenerateOrdersLoop() {
        List<BuildingResult> res = build.building(generateOrderItems())
                .stream().toList();
        assertThat(res).usingRecursiveComparison()
                        .isEqualTo(getBuildingResults());
    }

    private Collection<BuildOrder> generateOrderItems() {
        List<BuildOrder> orders = new ArrayList<>();
        for(int idx = 0; idx < 3; idx++) {
            orders.add(generateOrderItem(idx));
        }
        return orders;
    }

    private BuildOrder generateOrderItem(int idx) {
        FoundationTypes[] foundations = FoundationTypes.values();
        WallsTypes[] wallsTypes = WallsTypes.values();

        return new BuildOrder(foundations[idx].getTypeName(),
                wallsTypes[idx].getTypeName(),
                idx);
    }

    private Collection<BuildingResult> getBuildingResults() {
        FoundationTypes[] foundations = FoundationTypes.values();
        WallsTypes[] wallsTypes = WallsTypes.values();

        List<BuildingResult> buildingResults = new ArrayList<>();
        for(int idx = 0; idx < 3; idx++) {
            buildingResults.add(new BuildingResult(foundations[idx],
                    wallsTypes[idx],
                    idx));
        }
        return buildingResults;
    }
}
