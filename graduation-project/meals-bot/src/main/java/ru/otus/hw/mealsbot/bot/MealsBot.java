package ru.otus.hw.mealsbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.hw.mealsbot.bot.message_handling.MessageParser;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MealsBot extends TelegramLongPollingBot {
    private final BotConfig config;

    private final MessageParser parser;

    public MealsBot(BotConfig config, MessageParser messageParser) {
        super(config.getToken());
        this.config = config;
        this.parser = messageParser;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        String receivedMessage = null;
        String username = null;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            username = update.getMessage().getFrom().getUserName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
            }
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            receivedMessage = update.getCallbackQuery().getData();
            username = update.getCallbackQuery().getFrom().getUserName();
        }
        var sm = parser.parse(username, chatId, receivedMessage);
        if (sm != null) {
            sendMsgWithRetry(sm);
        }
    }

    private void sendMsgWithRetry(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException ex) {
            retry(sendMessage);
        }
    }

    private void retry(SendMessage sendMessage) {
        try {
            if (sendMessage.getText().length() < 2048) {
                SendMessage sm = new SendMessage();
                sm.setChatId(sendMessage.getChatId());
                sm.setText(sendMessage.getText());
                execute(sm);
            } else {
                sendLongMsg(sendMessage);
            }
        } catch (TelegramApiException e) {
            log.error("send ex", e);
        }
    }

    private void sendLongMsg(SendMessage sendMessage)
            throws TelegramApiException {
        List<String> textList = splitStr(sendMessage.getText(), 2048);
        for (String text : textList) {
            SendMessage sm = new SendMessage();
            sm.setChatId(sendMessage.getChatId());
            sm.setText(text);
            execute(sm);
        }
        SendMessage sm = new SendMessage();
        sm.setText("Выберите один из вариантов ниже");
        sm.setChatId(sendMessage.getChatId());
        sm.setReplyMarkup(sendMessage.getReplyMarkup());
        execute(sm);
    }

    private List<String> splitStr(String text, int n) {
        List<String> results = new ArrayList<>();
        int length = text.length();

        for (int i = 0; i < length; i += n) {
            results.add(text.substring(i, Math.min(length, i + n)));
        }

        return results;
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }
}
