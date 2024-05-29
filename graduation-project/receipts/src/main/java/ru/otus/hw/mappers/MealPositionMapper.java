package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.meal_positions.MealPositionCreateDto;
import ru.otus.hw.dto.meal_positions.MealPositionDto;
import ru.otus.hw.dto.meal_positions.MealPositionUpdateDto;
import ru.otus.hw.models.Meal;
import ru.otus.hw.models.MealPosition;
import ru.otus.hw.models.Serving;

@Component
@AllArgsConstructor
public class MealPositionMapper {

    private final MealMapper mealMapper;

    private final ServingMapper servingMapper;

    public MealPosition toModel(MealPositionDto dto) {
        return new MealPosition(dto.getId(), mealMapper.toModel(dto.getMeal()),
                servingMapper.toModel(dto.getServing()), dto.getQuantity());
    }

    public MealPosition toModel(MealPositionCreateDto dto, Meal meal,
                                Serving serving) {
        return new MealPosition(null, meal, serving, dto.getQuantity());
    }

    public MealPosition toModel(MealPositionUpdateDto dto, Meal meal,
                                Serving serving) {
        return new MealPosition(null, meal, serving, dto.getQuantity());
    }

    public MealPositionDto toDto(MealPosition model) {
        return new MealPositionDto(model.getId(), mealMapper.toDto(model.getMeal()),
                servingMapper.toDto(model.getServing()), model.getQuantity());
    }
}
