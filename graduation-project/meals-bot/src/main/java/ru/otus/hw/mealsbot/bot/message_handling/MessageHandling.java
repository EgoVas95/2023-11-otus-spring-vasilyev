package ru.otus.hw.mealsbot.bot.message_handling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.otus.hw.mealsbot.bot.commands.Buttons;
import ru.otus.hw.mealsbot.bot.commands.CommandsEnum;
import ru.otus.hw.mealsbot.converter.DayConverter;
import ru.otus.hw.mealsbot.converter.PricePositionConverter;
import ru.otus.hw.mealsbot.proxy.PlanControllerProxy;
import ru.otus.hw.mealsbot.proxy.ReceiptsControllerProxy;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
@EnableFeignClients("ru.otus.hw.mealsbot.proxy")
@Slf4j
public class MessageHandling {
    private final PlanControllerProxy planControllerProxy;

    private final ReceiptsControllerProxy receiptsControllerProxy;

    private final DayConverter dayConverter;

    private final PricePositionConverter priceConverter;

    public SendMessage handleCommand(String username, CommandsEnum command, long chatId, String receivedMessage) {
        if (receivedMessage == null || receivedMessage.isEmpty()) {
            return null;
        }

        return switch (command) {
            case START, RESET -> startBot(chatId);
            case GENERATE_MEALS -> generateProcess(username, chatId, "");
            case SHOW_MEALS -> showMeals(username, chatId);
            case SHOW_BUY -> showBuys(username, chatId);
            default -> null;
        };
    }

    private SendMessage showBuys(String username, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(Buttons.startBtns());
        message.setText(priceConverter.toString(planControllerProxy.geBuyMap(username)));
        return message;
    }

    private SendMessage showMeals(String username, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(Buttons.startBtns());
        message.setText(dayConverter.convertList(planControllerProxy.getMealsForUser(username)));
        return message;
    }

    public SendMessage generateProcess(String username, long chatId, String msg) {
        String[] vals = msg.split(Buttons.DELIMITIER);

        String dietId = vals.length > 1 ? vals[1] : "";
        String calories = vals.length > 2 ? vals[2] : "";
        int dayCount = -1;
        if (vals.length > 3) {
            try {
                dayCount = Integer.parseInt(vals[3]);
            } catch (Exception ex) {
                log.error("day count is not valid", ex);
            }
        }

        return getGenerateMsg(username, chatId, dietId, calories, dayCount);
    }

    private SendMessage getGenerateMsg(String username, long chatId, String dietId, String calories, int dayCount) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (!StringUtils.hasLength(dietId)) {
            message.setText("Выберите диету");
            message.setReplyMarkup(Buttons.dietBtns(receiptsControllerProxy.getDietTypes()));
        } else if (!StringUtils.hasLength(calories)) {
            message.setText("Выберите калораж");
            message.setReplyMarkup(Buttons.caloriesBtns(
                    dietId, receiptsControllerProxy.getCaloriesTypes()));
        } else if (StringUtils.hasLength(dietId) && StringUtils.hasLength(calories) &&
                dayCount < 0) {
            message.setText("Выберите количество дней");
            message.setReplyMarkup(Buttons.mealsButtons(dietId, calories));
        } else {
            message = generateMeals(username, dietId, calories, chatId, dayCount);
        }
        return message;
    }

    private SendMessage generateMeals(String username, String dietId,
                                      String caloriesId, long chatId, int dayCount) {
        if (dayCount <= 0) {
            Calendar cal = Calendar.getInstance();
            dayCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        String msg = dayConverter.convertList(planControllerProxy
                .mealList(username, dietId, caloriesId, dayCount));

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(msg);
        message.setReplyMarkup(Buttons.startBtns());

        return message;
    }

    private SendMessage startBot(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(Buttons.startBtns());
        message.setText("Выберите один из вариантов ниже");
        return message;
    }
}
