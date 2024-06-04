package ru.otus.hw.mealconfigurator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.mealconfigurator.model.Day;
import ru.otus.hw.mealconfigurator.model.PricePosition;
import ru.otus.hw.mealconfigurator.model.receipt_models.Receipt;
import ru.otus.hw.mealconfigurator.model.receipt_models.Serving;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyMapGeneratorImpl implements BuyMapGenerator {

    private final DayServiceImpl dayService;

    @Override
    public List<PricePosition> generateBuyList(String username) {
        Map<Serving, Long> resultMap = new HashMap<>();
        dayService.findAllByUserId(username)
                .forEach(day -> buyMapForDay(resultMap, day));

        return resultMap.keySet().stream()
                .map(serving -> new PricePosition(serving, resultMap.get(serving)))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Serving, Long> generateBuyMapByDay(String username, LocalDate date) {
        return buyMapForDay(dayService.findFirstByUserIdAndDate(
                username, date));
    }

    private Map<Serving, Long> buyMapForDay(Day day) {
        return buyMapForDay(new HashMap<>(), day);
    }

    private Map<Serving, Long> buyMapForDay(Map<Serving, Long> resultMap, Day day) {
        day.getReceiptList().forEach(receipt -> buyMapForMeal(resultMap, receipt));
        return resultMap;
    }

    private Map<Serving, Long> buyMapForMeal(Map<Serving, Long> resultMap, Receipt receipt) {
        receipt.getReceiptPositionsList().forEach(position -> {
            Serving serving = position.getServing();
            Long quantity = position.getQuantity();
            if (resultMap.containsKey(serving)) {
                quantity = resultMap.get(serving) + position.getQuantity();
            }
            resultMap.put(serving, quantity);
        });
        return resultMap;
    }
}
