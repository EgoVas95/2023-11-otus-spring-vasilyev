package ru.otus.hw.mealconfigurator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.mealconfigurator.converter.MealListConverter;
import ru.otus.hw.mealconfigurator.dto.DayDto;
import ru.otus.hw.mealconfigurator.logic.UserService;
import ru.otus.hw.mealconfigurator.model.MealList;
import ru.otus.hw.mealconfigurator.repositories.MealListRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealListServiceImpl implements MealListService {
    private final MealListRepository repository;

    private final MealListConverter converter;

    private UserService userService;

    @Override
    public MealList save(List<DayDto> daysList) {
        String id = null;
        var find = findForUser();
        if (find != null) {
            id = find.getId();
        }
        var mealList = converter.fromDaysList(daysList);
        mealList.setId(id);
        return repository.save(mealList);
    }

    @Override
    public MealList findForUser() {
        return repository.findFirstByUserSub(userService.getCurrentUserSub());
    }
}
