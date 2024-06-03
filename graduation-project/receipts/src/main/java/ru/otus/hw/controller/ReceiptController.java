package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.Receipt;
import ru.otus.hw.services.receipt.ReceiptServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptServiceImpl service;

    @GetMapping("/api/receipts")
    public List<Receipt> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/receipts/{receipt_id}")
    public Receipt findById(@PathVariable("receipt_id") String id) {
        return service.findById(id);
    }

    @GetMapping("/api/receipts/food/{mealtime_id}/{diet_type_id}/{calories_type_id}")
    public List<Receipt> findByParams(@PathVariable("mealtime_id") String mealtimeId,
                                      @PathVariable("diet_type_id") String dietTypeId,
                                      @PathVariable("calories_type_id") String caloriesTypeId) {

        return service.findAllByMealtimeIdAndDietTypeIdAndAndCaloriesTypeId(mealtimeId,
                dietTypeId, caloriesTypeId);
    }

    @PostMapping("/api/receipts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Receipt create(@Valid @RequestBody Receipt receipt) {
        return service.create(receipt);
    }

    @PatchMapping("/api/receipts/{receipt_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Receipt create(@PathVariable("receipt_id") String id,
                          @Valid @RequestBody Receipt receipt) {
        receipt.setId(id);
        return service.update(receipt);
    }

    @DeleteMapping("/api/receipts/{receipt_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("receipt_id") String id) {
        service.delete(id);
    }
}
