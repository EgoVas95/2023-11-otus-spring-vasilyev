package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.meal.MealDto;
import ru.otus.hw.dto.meal.MealUpdateDto;
import ru.otus.hw.models.CaloriesType;
import ru.otus.hw.models.DietType;
import ru.otus.hw.models.Meal;
import ru.otus.hw.models.MealtimeType;

@Component
@AllArgsConstructor
public class MealMapper {

    private final MealtimeTypeMapper mealtimeTypeMapper;

    private final DietTypeMapper dietTypeMapper;

    private final CaloriesTypeMapper caloriesTypeMapper;

    public Meal toModel(MealDto dto) {
        return new Meal(dto.getId(), mealtimeTypeMapper.toModel(dto.getMealtimeTypeDto()),
                dietTypeMapper.toModel(dto.getDietTypeDto()),
                caloriesTypeMapper.toModel(dto.getCaloriesDto()));
    }

    public Meal toModel(MealtimeType mealtimeType, DietType dietType,
                        CaloriesType caloriesType) {
        return new Meal(null, mealtimeType, dietType, caloriesType);
    }

    public Meal toModel(MealUpdateDto dto, MealtimeType mealtimeType,
                        DietType dietType, CaloriesType caloriesType) {
        return new Meal(dto.getId(), mealtimeType, dietType, caloriesType);
    }

    public MealDto toDto(Meal model) {
        return new MealDto(model.getId(), mealtimeTypeMapper.toDto(model.getMealtimeType()),
                dietTypeMapper.toDto(model.getDietType()),
                caloriesTypeMapper.toDto(model.getCaloriesType()));
    }

}
