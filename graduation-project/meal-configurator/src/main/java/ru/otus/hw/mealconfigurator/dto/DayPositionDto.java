package ru.otus.hw.mealconfigurator.dto;

import lombok.Data;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.FoodDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.MealtimeTypeDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.ReceiptDto;
import ru.otus.hw.mealconfigurator.dto.proxy_dto.ServingDto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class DayPositionDto {

    public DayPositionDto(MealtimeTypeDto typeDto) {
        this.mealtimeTypeDto = typeDto;
    }

    private MealtimeTypeDto mealtimeTypeDto;

    private Map<ServingDto, BigDecimal> servingQuantityMap = new HashMap<>();

    private BigDecimal caloriesSum = BigDecimal.ZERO;

    private BigDecimal proteinSum = BigDecimal.ZERO;

    private BigDecimal fatsSum = BigDecimal.ZERO;

    private BigDecimal carbohydratesSum = BigDecimal.ZERO;

    public void addServing(ServingDto serving, BigDecimal quantity) {
        if (serving == null || serving.getFoodDto() == null
                || quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        servingQuantityMap.put(serving, quantity);

        addCalories(serving.getCalories(), quantity);
        addProteins(serving.getProteins(), quantity);
        addFats(serving.getFats(), quantity);
        addCarbohydrates(serving.getCarbohydrates(), quantity);
    }

    private void addCalories(BigDecimal calories, BigDecimal multiple) {
        if (calories != null && multiple != null) {
            caloriesSum = caloriesSum.add(calories.multiply(multiple));
        }
    }

    public void addProteins(BigDecimal proteins, BigDecimal multiple) {
        if (proteins != null) {
            proteinSum = proteinSum.add(proteins.multiply(multiple));
        }
    }

    private void addFats(BigDecimal fats, BigDecimal multiple) {
        if (fats != null) {
            fatsSum = fatsSum.add(fats.multiply(multiple));
        }
    }

    private void addCarbohydrates(BigDecimal carbohydrates, BigDecimal multiple) {
        if (carbohydrates != null) {
            caloriesSum = carbohydratesSum.add(carbohydrates.multiply(multiple));
        }
    }
}
