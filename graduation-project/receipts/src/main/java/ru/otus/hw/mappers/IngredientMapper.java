package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.ingredient.IngredientCreateDto;
import ru.otus.hw.dto.ingredient.IngredientDto;
import ru.otus.hw.dto.ingredient.IngredientUpdateDto;
import ru.otus.hw.models.Ingredient;
import ru.otus.hw.models.Receipt;
import ru.otus.hw.models.Serving;

@Component
@AllArgsConstructor
public class IngredientMapper {
    private final ReceiptMapper receiptMapper;

    private final ServingMapper servingMapper;

    public Ingredient toModel(IngredientDto dto) {
        return new Ingredient(dto.getId(), receiptMapper.toModel(dto.getReceiptDto()),
                servingMapper.toModel(dto.getServingDto()), dto.getQuantity());
    }

    public Ingredient toModel(IngredientCreateDto dto, Receipt receipt, Serving serving) {
        return new Ingredient(null, receipt, serving, dto.getQuantity());
    }

    public Ingredient toModel(IngredientUpdateDto dto, Receipt receipt, Serving serving) {
        return new Ingredient(dto.getId(), receipt, serving, dto.getQuantity());
    }

    public IngredientDto toDto(Ingredient ingredient) {
        return new IngredientDto(ingredient.getId(), receiptMapper.toDto(ingredient.getReceipt()),
                servingMapper.toDto(ingredient.getServing()), ingredient.getQuantity());
    }
}
