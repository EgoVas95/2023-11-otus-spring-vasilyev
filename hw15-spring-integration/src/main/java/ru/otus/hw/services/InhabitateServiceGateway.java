package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.domain.FinishedBuild;
import ru.otus.hw.domain.PrepareBuildResult;

import java.util.Collection;

@MessagingGateway
public interface InhabitateServiceGateway {
    @Gateway(requestChannel = "prepareBuildChannel", replyChannel = "inhabitedChannel")
    Collection<FinishedBuild> inhabitate(Collection<PrepareBuildResult> res);
}
