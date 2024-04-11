package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.domain.BuildOrder;
import ru.otus.hw.domain.BuildingResult;

import java.util.Collection;

@MessagingGateway
public interface BuildServiceGateway {
    @Gateway(requestChannel = "orderChannel", replyChannel = "buildingChannel")
    Collection<BuildingResult> building(Collection<BuildOrder> orders);
}
