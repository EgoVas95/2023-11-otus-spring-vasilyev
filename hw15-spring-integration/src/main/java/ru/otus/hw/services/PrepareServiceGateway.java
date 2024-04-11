package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.domain.BuildingResult;
import ru.otus.hw.domain.PrepareBuildResult;

import java.util.Collection;

@MessagingGateway
public interface PrepareServiceGateway {
    @Gateway(requestChannel = "buildingChannel", replyChannel = "prepareBuildChannel")
    Collection<PrepareBuildResult> prepare(Collection<BuildingResult> res);
}
