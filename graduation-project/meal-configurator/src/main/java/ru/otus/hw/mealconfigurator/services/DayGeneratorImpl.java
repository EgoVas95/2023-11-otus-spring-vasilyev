package ru.otus.hw.mealconfigurator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import ru.otus.hw.mealconfigurator.model.Day;
import ru.otus.hw.mealconfigurator.model.receipt_models.Mealtime;
import ru.otus.hw.mealconfigurator.model.receipt_models.Receipt;
import ru.otus.hw.mealconfigurator.proxies.ReceiptsControllerProxy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@EnableFeignClients("ru.otus.hw.mealconfigurator.proxies")
@RequiredArgsConstructor
public class DayGeneratorImpl implements DayGenerator {

    private final ReceiptsControllerProxy proxy;

    private final DayServiceImpl dayService;

    @Override
    public List<Day> generate(String username, String dietTypeId, String caloriesTypeId, int dayCount) {
        dayService.deleteAllByUserId(username);
        if (dayCount <= 0) {
            return Collections.emptyList();
        }
        LocalDate date = LocalDate.now();
        List<Day> result = new ArrayList<>(dayCount);

        for (int idx = 0; idx < dayCount; idx++) {
            Day day = generateOneDay(username, date, dietTypeId, caloriesTypeId);
            day = dayService.create(day);
            result.add(day);

            date = date.plusDays(1);
        }
        return result;
    }

    private Day generateOneDay(String username, LocalDate date, String dietTypeId, String caloriesTypeId) {
        List<Receipt> receipts = new ArrayList<>();
        proxy.getMealtimes().forEach(mealtime ->
                receipts.add(generateOneReceipt(mealtime, dietTypeId, caloriesTypeId)));
        return new Day(null, username, date, receipts);
    }

    private Receipt generateOneReceipt(Mealtime mealtime,
                                       String dietTypeId, String caloriesTypeId) {
        Random random = new Random();
        List<Receipt> receiptList = proxy.getReceiptForMeal(mealtime.getId(), dietTypeId, caloriesTypeId);
        if (receiptList.isEmpty()) {
            return null;
        }
        return receiptList.get(random.nextInt(0, receiptList.size()));
    }
}
