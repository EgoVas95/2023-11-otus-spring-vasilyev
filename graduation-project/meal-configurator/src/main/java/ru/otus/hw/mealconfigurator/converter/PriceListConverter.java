package ru.otus.hw.mealconfigurator.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.mealconfigurator.dto.DayDto;
import ru.otus.hw.mealconfigurator.dto.PricePositionDto;
import ru.otus.hw.mealconfigurator.model.PriceList;
import ru.otus.hw.mealconfigurator.logic.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PriceListConverter {

    private final UserService userService;

    private final PricePositionConverter converter;

    public PriceList fromPositions(List<DayDto> dayDtoList) {
        PriceList priceList = new PriceList();
        priceList.setUserSub(userService.getCurrentUserSub());
        priceList.setPricePositionList(new ArrayList<>(positionMap.keySet().size()));

        positionMap.keySet().forEach(position ->
                priceList.getPricePositionList().add(
                        converter.convertPricePosition(position, positionMap.get(position))));
        return priceList;
    }

    public String convertPriceListToString(PriceList priceList) {
        if (priceList == null) {
            return "";
        }
        return priceList.getPricePositionList().stream()
                .collect(Collectors.joining("\r\n", "", ""));
    }
}
