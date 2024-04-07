package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.BuildingResult;
import ru.otus.hw.domain.PrepareBuildResult;

@Service
@Slf4j
public class PrepareBuildServiceImp implements PrepareBuildService {
    @Override
    public PrepareBuildResult prepare(BuildingResult result) {
        log.info("Prepare build {}", result.toString());
        PrepareBuildResult prepareBuildResult =  new PrepareBuildResult(result);
        log.info("Connect water");
        prepareBuildResult.setHasWaterSupply(true);
        log.info("Connect electric");
        prepareBuildResult.setHasElectric(true);
        log.info("Connect heat");
        prepareBuildResult.setHasHeating(true);
        return prepareBuildResult;
    }
}
