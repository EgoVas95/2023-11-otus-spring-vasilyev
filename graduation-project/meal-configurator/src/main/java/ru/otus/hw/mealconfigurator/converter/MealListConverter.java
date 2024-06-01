package ru.otus.hw.mealconfigurator.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealconfigurator.dto.DayDto;
import ru.otus.hw.mealconfigurator.model.MealList;
import ru.otus.hw.mealconfigurator.logic.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MealListConverter {

    private final UserService userService;

    private final DayConverter converter;

    public MealList fromDaysList(List<DayDto> daysList) {
        MealList mealList = new MealList();
        mealList.setDayByStringList(new ArrayList<>(daysList.size()));
        mealList.setUserSub(userService.getCurrentUserSub());
        daysList.forEach(day -> mealList.getDayByStringList().add(converter.convertDay(day)));
        return mealList;
    }

    public String mealListToString(MealList mealList) {
        return mealList.getDayByStringList().stream()
                .collect(Collectors.joining("\r\n", "", ""));
    }
}
