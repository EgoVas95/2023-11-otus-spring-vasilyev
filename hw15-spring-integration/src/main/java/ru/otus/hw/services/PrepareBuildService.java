package ru.otus.hw.services;

import ru.otus.hw.domain.BuildingResult;
import ru.otus.hw.domain.PrepareBuildResult;

public interface PrepareBuildService {
    PrepareBuildResult prepare(BuildingResult result);
}
