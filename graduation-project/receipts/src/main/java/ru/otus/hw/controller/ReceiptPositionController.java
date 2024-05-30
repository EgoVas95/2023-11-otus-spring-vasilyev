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
import ru.otus.hw.dto.receipt_position.ReceiptPositionCreateDto;
import ru.otus.hw.dto.receipt_position.ReceiptPositionDto;
import ru.otus.hw.dto.receipt_position.ReceiptPositionUpdateDto;
import ru.otus.hw.services.receipt_position.ReceiptPositionServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReceiptPositionController {
    private final ReceiptPositionServiceImpl service;

    @GetMapping("/api/ingredients")
    public List<ReceiptPositionDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/api/ingredients/{ingredient_id}")
    public ReceiptPositionDto getById(@PathVariable("ingredient_id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/api/receipts/ingredients/{receipt_id}")
    public List<ReceiptPositionDto> getByReceiptId(@PathVariable("receipt_id") Long id) {
        return service.findByReceiptId(id);
    }

    @PostMapping("/api/ingredients")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReceiptPositionDto create(@Valid @RequestBody ReceiptPositionCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/ingredients/{ingredient_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ReceiptPositionDto update(@PathVariable("ingredient_id") Long id,
                                     @Valid @RequestBody ReceiptPositionUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/ingredients/{ingredient_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("ingredient_id") Long id) {
        service.delete(id);
    }
}
