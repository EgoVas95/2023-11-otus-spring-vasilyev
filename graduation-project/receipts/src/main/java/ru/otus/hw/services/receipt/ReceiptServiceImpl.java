package ru.otus.hw.services.receipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.receipt.ReceiptCreateDto;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.dto.receipt.ReceiptUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.ReceiptMapper;
import ru.otus.hw.repositories.FoodRepository;
import ru.otus.hw.repositories.ReceiptsRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptMapper receiptMapper;


    private final ReceiptsRepository receiptsRepository;

    private final FoodRepository foodRepository;

    @Override
    public ReceiptDto findById(Long id) {
        return receiptMapper.toDto(receiptsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Рецепт с id = %d не найден!".formatted(id))));
    }

    @Override
    public List<ReceiptDto> findAll() {
        return receiptsRepository.findAll()
                .stream().map(receiptMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReceiptDto> findAllByFoodId(Long id) {
        return receiptsRepository.findAllByFoodId(id)
                .stream().map(receiptMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReceiptDto create(ReceiptCreateDto dto) {
        var foodId = dto.getFoodId();
        var food = foodRepository.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Продукт с id = %d не найден!".formatted(foodId)));

        return receiptMapper.toDto(receiptsRepository.save(
                receiptMapper.toModel(dto, food)));
    }

    @Override
    @Transactional
    public ReceiptDto update(ReceiptUpdateDto dto) {
        var foodId = dto.getFoodId();
        var food = foodRepository.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Продукт с id = %d не найден!".formatted(foodId)));

        return receiptMapper.toDto(receiptsRepository.save(
                receiptMapper.toModel(dto, food)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        receiptsRepository.deleteById(id);
    }
}
