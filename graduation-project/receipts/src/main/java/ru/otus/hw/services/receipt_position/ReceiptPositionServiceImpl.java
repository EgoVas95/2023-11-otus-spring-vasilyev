package ru.otus.hw.services.receipt_position;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.receipt_position.ReceiptPositionCreateDto;
import ru.otus.hw.dto.receipt_position.ReceiptPositionDto;
import ru.otus.hw.dto.receipt_position.ReceiptPositionUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.ReceiptPositionMapper;
import ru.otus.hw.repositories.ReceiptPositionRepository;
import ru.otus.hw.repositories.ReceiptsRepository;
import ru.otus.hw.repositories.ServingRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReceiptPositionServiceImpl implements ReceiptPositionService {

    private final ReceiptPositionMapper receiptPositionMapper;


    private final ReceiptPositionRepository receiptPositionRepository;

    private final ReceiptsRepository receiptsRepository;

    private final ServingRepository servingRepository;

    @Override
    public List<ReceiptPositionDto> findAll() {
        return receiptPositionRepository.findAll()
                .stream().map(receiptPositionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReceiptPositionDto findById(Long id) {
        return receiptPositionMapper.toDto(receiptPositionRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Ингредиент с id = %d не найден!".formatted(id))));
    }

    @Override
    public List<ReceiptPositionDto> findByReceiptId(Long id) {
        return receiptPositionRepository.findAllByReceiptId(id).stream()
                .map(receiptPositionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReceiptPositionDto create(ReceiptPositionCreateDto dto) {
        var receiptId = dto.getReceiptId();
        var receipt = receiptsRepository.findById(receiptId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Рецепт с id = %d не найден!".formatted(receiptId)));

        var serviceId = dto.getServingId();
        var service = servingRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Порция с id = %d не найдена!"));

        var ingredient = receiptPositionMapper.toModel(dto, receipt, service);
        return receiptPositionMapper.toDto(receiptPositionRepository.save(ingredient));
    }

    @Override
    @Transactional
    public ReceiptPositionDto update(ReceiptPositionUpdateDto dto) {
        var receiptId = dto.getReceiptId();
        var receipt = receiptsRepository.findById(receiptId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Рецепт с id = %d не найден!".formatted(receiptId)));

        var serviceId = dto.getServingId();
        var service = servingRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Порция с id = %d не найдена!"));

        return receiptPositionMapper.toDto(receiptPositionRepository.save(
                receiptPositionMapper.toModel(dto, receipt, service)
        ));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        receiptPositionRepository.deleteById(id);
    }
}
