package ru.otus.hw.services.ingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.ingredient.IngredientCreateDto;
import ru.otus.hw.dto.ingredient.IngredientDto;
import ru.otus.hw.dto.ingredient.IngredientUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.IngredientMapper;
import ru.otus.hw.repositories.IngredientRepository;
import ru.otus.hw.repositories.ReceiptsRepository;
import ru.otus.hw.repositories.ServingRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientMapper ingredientMapper;


    private final IngredientRepository ingredientRepository;

    private final ReceiptsRepository receiptsRepository;

    private final ServingRepository servingRepository;

    @Override
    public List<IngredientDto> findAll() {
        return ingredientRepository.findAll()
                .stream().map(ingredientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDto findById(Long id) {
        return ingredientMapper.toDto(ingredientRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Ингредиент с id = %d не найден!".formatted(id))));
    }

    @Override
    public List<IngredientDto> findByReceiptId(Long id) {
        return ingredientRepository.findAllByReceiptId(id).stream()
                .map(ingredientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IngredientDto create(IngredientCreateDto dto) {
        var receiptId = dto.getReceiptId();
        var receipt = receiptsRepository.findById(receiptId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Рецепт с id = %d не найден!".formatted(receiptId)));

        var serviceId = dto.getServingId();
        var service = servingRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Порция с id = %d не найдена!"));

        var ingredient = ingredientMapper.toModel(dto, receipt, service);
        return ingredientMapper.toDto(ingredientRepository.save(ingredient));
    }

    @Override
    @Transactional
    public IngredientDto update(IngredientUpdateDto dto) {
        var receiptId = dto.getReceiptId();
        var receipt = receiptsRepository.findById(receiptId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Рецепт с id = %d не найден!".formatted(receiptId)));

        var serviceId = dto.getServingId();
        var service = servingRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Порция с id = %d не найдена!"));

        return ingredientMapper.toDto(ingredientRepository.save(
                ingredientMapper.toModel(dto, receipt, service)
        ));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }
}
