package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.FinishedBuild;
import ru.otus.hw.domain.PrepareBuildResult;

@Service
@Slf4j
public class InhabitateServiceImp implements InhabitateService {
    @Override
    public FinishedBuild inhabitate(PrepareBuildResult res) {
        log.info("Inhabitate {}", res.toString());
        return new FinishedBuild(res, true);
    }
}
