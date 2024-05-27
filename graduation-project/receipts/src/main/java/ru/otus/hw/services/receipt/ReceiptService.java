package ru.otus.hw.services.receipt;

import ru.otus.hw.dto.receipt.ReceiptCreateDto;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.dto.receipt.ReceiptUpdateDto;

import java.util.List;

public interface ReceiptService {

    ReceiptDto findById(Long id);

    List<ReceiptDto> findAll();

    List<ReceiptDto> findAllByFoodId(Long id);

    ReceiptDto create(ReceiptCreateDto dto);

    ReceiptDto update(ReceiptUpdateDto dto);

    void delete(Long id);
}
