package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.serving.ServingCreateDto;
import ru.otus.hw.dto.serving.ServingDto;
import ru.otus.hw.dto.serving.ServingUpdateDto;
import ru.otus.hw.models.Food;
import ru.otus.hw.models.Serving;

@Component
@AllArgsConstructor
public class ServingMapper {
    private final FoodMapper foodMapper;

    public Serving toModel(ServingDto dto) {
        return new Serving(dto.getId(), dto.getName(),
                foodMapper.toModel(dto.getFoodDto()), dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
    }

    public Serving toModel(ServingCreateDto dto, Food food) {
        return new Serving(null, dto.getName(), food, dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
    }

    public Serving toModel(ServingUpdateDto dto, Food food) {
        return new Serving(dto.getId(), dto.getName(), food, dto.getCalories(),
                dto.getProteins(), dto.getFats(), dto.getCarbohydrates());
    }

    public ServingDto toDto(Serving serving) {
        return new ServingDto(serving.getId(), serving.getName(), foodMapper.toDto(serving.getFood()),
                serving.getCalories(), serving.getProteins(), serving.getFats(), serving.getCarbohydrates());
    }
}
