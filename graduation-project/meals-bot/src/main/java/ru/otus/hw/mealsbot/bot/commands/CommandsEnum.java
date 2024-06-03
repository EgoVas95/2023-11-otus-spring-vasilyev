package ru.otus.hw.mealsbot.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(exclude = "description")
public enum CommandsEnum {
    START("/start", "Запуск бота"),

    GENERATE_MEALS("/generate-meals", "Сгенерировать план питания"),
    GENERATE_MEALS_1("/meals-for-1", "день"),
    GENERATE_MEALS_7("/meals-for-7", "неделя"),
    GENERATE_MEALS_14("/meals-for-14", "14 дней"),
    GENERATE_MEALS_30("/meals-for-30", "месяц"),

    SHOW_MEALS("/show-meals", "Показать план питания"),
    SHOW_BUY("/show-buy-list", "Сгенерировать список покупок"),

    RESET("/reset", "В главное меню");

    private final String commandName;

    private final String description;

}
