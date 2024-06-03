package ru.otus.hw.models;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "receipts")
public class Receipt {
    @Id
    private String id;

    @NotBlank(message = "Укажите название блюда!")
    private String name;

    @Valid
    private CaloriesType caloriesType;

    @Valid
    private DietType dietType;

    @Valid
    private Mealtime mealtime;

    @Valid
    private List<ReceiptPosition> receiptPositionsList;
}
