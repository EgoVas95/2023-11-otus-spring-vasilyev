package ru.otus.hw.services;

import ru.otus.hw.domain.BuildOrder;
import ru.otus.hw.domain.BuildingResult;

public interface BuildService {
    BuildingResult build(BuildOrder order);
}
