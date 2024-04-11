package ru.otus.hw.services;

import ru.otus.hw.domain.FinishedBuild;
import ru.otus.hw.domain.PrepareBuildResult;

public interface InhabitateService {
    FinishedBuild inhabitate(PrepareBuildResult res);
}
