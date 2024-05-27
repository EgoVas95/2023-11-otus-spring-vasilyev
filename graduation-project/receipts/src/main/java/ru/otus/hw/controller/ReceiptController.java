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
import ru.otus.hw.dto.receipt.ReceiptCreateDto;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.dto.receipt.ReceiptUpdateDto;
import ru.otus.hw.services.receipt.ReceiptServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptServiceImpl service;

    @GetMapping("/api/receipts")
    public List<ReceiptDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/receipts/{receipt_id}")
    public ReceiptDto findById(@PathVariable("receipt_id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/api/receipts/food/{food_id}")
    public List<ReceiptDto> findByFoodId(@PathVariable("food_id") Long id) {
        return service.findAllByFoodId(id);
    }

    @PostMapping("/api/receipts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReceiptDto create(@Valid @RequestBody ReceiptCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/receipts/{receipt_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ReceiptDto update(@PathVariable("receipt_id") Long id,
                             @Valid @RequestBody ReceiptUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/receipts/{receipt_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("receipt_id") Long id) {
        service.delete(id);
    }
}
