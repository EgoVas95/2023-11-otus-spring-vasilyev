package ru.otus.hw.mealsbot.bot.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.otus.hw.mealsbot.models.CaloriesType;
import ru.otus.hw.mealsbot.models.DietType;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.hw.mealsbot.bot.commands.CommandsEnum.GENERATE_MEALS;
import static ru.otus.hw.mealsbot.bot.commands.CommandsEnum.GENERATE_MEALS_1;
import static ru.otus.hw.mealsbot.bot.commands.CommandsEnum.GENERATE_MEALS_30;
import static ru.otus.hw.mealsbot.bot.commands.CommandsEnum.GENERATE_MEALS_7;
import static ru.otus.hw.mealsbot.bot.commands.CommandsEnum.RESET;
import static ru.otus.hw.mealsbot.bot.commands.CommandsEnum.SHOW_MEALS;
import static ru.otus.hw.mealsbot.bot.commands.CommandsEnum.SHOW_BUY;

public class Buttons {
    public static final String GENERATE_MARK = "gn-mk";

    public static final String DELIMITIER = "_";

    public static InlineKeyboardMarkup startBtns() {
        List<InlineKeyboardButton> rowInline = List.of(getButtonFromCommands(GENERATE_MEALS),
                getButtonFromCommands(SHOW_MEALS), getButtonFromCommands(SHOW_BUY));
        List<List<InlineKeyboardButton>> rowsInLine = List.of(List.of(getButtonFromCommands(GENERATE_MEALS)),
                List.of(getButtonFromCommands(SHOW_MEALS)), List.of(getButtonFromCommands(SHOW_BUY)));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

    public static InlineKeyboardMarkup dietBtns(List<DietType> dietTypes) {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>(dietTypes.size());
        for (var type : dietTypes) {
            InlineKeyboardButton btn = new InlineKeyboardButton(type.getName());
            btn.setCallbackData("%s%s%s".formatted(GENERATE_MARK,
                    DELIMITIER, type.getId()));
            rowsInLine.add(List.of(btn));
        }
        rowsInLine.add(List.of(getButtonFromCommands(RESET)));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);
        return markupInline;
    }

    public static InlineKeyboardMarkup caloriesBtns(String dietId, List<CaloriesType> mealtimes) {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>(mealtimes.size());
        for (var type : mealtimes) {
            InlineKeyboardButton btn = new InlineKeyboardButton(type.getCalories() + " ккал");
            btn.setCallbackData(GENERATE_MARK + DELIMITIER + dietId + DELIMITIER +
                    type.getId());
            rowsInLine.add(List.of(btn));
        }
        rowsInLine.add(List.of(getButtonFromCommands(RESET)));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);
        return markupInline;
    }

    public static InlineKeyboardMarkup mealsButtons(String dietId, String mealId) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>(4);

        InlineKeyboardButton btn = new InlineKeyboardButton(GENERATE_MEALS_1.getDescription());
        btn.setCallbackData(GENERATE_MARK + DELIMITIER + dietId +
                        DELIMITIER + mealId + DELIMITIER + 1);
        rowInline.add(btn);
        btn = new InlineKeyboardButton(GENERATE_MEALS_7.getDescription());
        btn.setCallbackData(GENERATE_MARK + DELIMITIER + dietId +
                DELIMITIER + mealId + DELIMITIER + 7);
        rowInline.add(btn);
        btn = new InlineKeyboardButton(GENERATE_MEALS_30.getDescription());
        btn.setCallbackData(GENERATE_MARK + DELIMITIER + dietId +
                DELIMITIER + mealId + DELIMITIER + 0);
        rowInline.add(btn);
        rowInline.add(getButtonFromCommands(RESET));

        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);
        return markupInline;
    }

    private static InlineKeyboardButton getButtonFromCommands(CommandsEnum commandsEnum) {
        return getButtonFromCommands(commandsEnum, commandsEnum.getCommandName());
    }

    private static InlineKeyboardButton getButtonFromCommands(CommandsEnum commandsEnum,
                                                              String callback) {
        InlineKeyboardButton btn = new InlineKeyboardButton(commandsEnum.getDescription());
        btn.setCallbackData(commandsEnum.getCommandName());
        return btn;
    }
}
