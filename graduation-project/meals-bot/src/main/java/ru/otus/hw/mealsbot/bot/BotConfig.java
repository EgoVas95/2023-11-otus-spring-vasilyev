package ru.otus.hw.mealsbot.bot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@ConfigurationProperties(prefix = "bot")
@PropertySource("classpath:application.yml")
public class BotConfig {
    private String name;

    private String token;
}
