package ru.otus.hw.mealsbot.bot.message_handling;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.otus.hw.mealsbot.bot.commands.Buttons;
import ru.otus.hw.mealsbot.bot.commands.CommandsEnum;

@Component
@RequiredArgsConstructor
public class MessageParser {
    private final MessageHandling messageHandling;

    public SendMessage parse(long chatId, String receivedMessage) {
        if (receivedMessage == null || receivedMessage.isEmpty()) {
            return null;
        }
        receivedMessage = receivedMessage.trim().toLowerCase();
        if (receivedMessage.startsWith(Buttons.GENERATE_MARK)) {
            return messageHandling.generateProcess(chatId, receivedMessage);
        }

        for (CommandsEnum command : CommandsEnum.values()) {
            if (command.getCommandName().equals(receivedMessage)) {
                return messageHandling.handleCommand(command, chatId, receivedMessage);
            }
        }
        return null;
    }
}
