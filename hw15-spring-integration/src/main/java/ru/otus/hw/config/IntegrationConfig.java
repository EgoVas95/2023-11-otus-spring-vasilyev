package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.hw.domain.FinishedBuild;
import ru.otus.hw.services.BuildService;
import ru.otus.hw.services.InhabitateService;
import ru.otus.hw.services.PrepareBuildService;

@Configuration
public class IntegrationConfig {
    @Bean
    public MessageChannelSpec<?, ?> orderChannel() {
        return MessageChannels.queue(20);
    }

    @Bean
    public MessageChannelSpec<?, ?> buildingChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> prepareBuildChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> inhabitedChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public IntegrationFlow buildFlow(BuildService service) {
        return IntegrationFlow.from(orderChannel())
                .split()
                .handle(service, "build")
                .aggregate()
                .channel(prepareBuildChannel())
                .get();
    }

    @Bean
    public IntegrationFlow prepareFlow(PrepareBuildService prepareService,
                                       InhabitateService inhabitateService) {
        return IntegrationFlow.from(prepareBuildChannel())
                .split()
                .handle(prepareService, "prepare")
                .handle(inhabitateService, "inhabitate")
                .aggregate()
                .channel(inhabitedChannel())
                .get();
    }
}
