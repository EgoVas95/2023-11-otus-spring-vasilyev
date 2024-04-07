package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.BuildOrder;
import ru.otus.hw.domain.BuildingResult;
import ru.otus.hw.domain.FoundationTypes;
import ru.otus.hw.domain.WallsTypes;

@Service
@Slf4j
public class BuildServiceImpl implements BuildService {
    @Override
    public BuildingResult build(BuildOrder order) {
        log.info("Start building {}", order.toString());
        return new BuildingResult(FoundationTypes.getFromTypeName(order.getFoundationType()),
                WallsTypes.getFromTypeName(order.getWallsType()),
                order.getFloorsNum());
    }
}
