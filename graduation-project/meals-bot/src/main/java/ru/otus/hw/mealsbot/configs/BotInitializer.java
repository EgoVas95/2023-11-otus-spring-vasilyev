package ru.otus.hw.mealsbot.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.otus.hw.mealsbot.bot.MealsBot;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotInitializer {
    private final MealsBot mealsBot;

    @EventListener({ContextRefreshedEvent.class})
    private void onContextRefreshed() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(mealsBot);
        } catch (TelegramApiException e) {
            log.error("Telegram register exception", e);
        }
    }
}
