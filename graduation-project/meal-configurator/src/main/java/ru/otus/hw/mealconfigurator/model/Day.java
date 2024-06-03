package ru.otus.hw.mealconfigurator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.hw.mealconfigurator.model.receipt_models.Receipt;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "days")
public class Day {
    @Id
    private String id;

    @NotBlank(message = "Идентификатор пользователя не может быть пустым!")
    private String userId;

    @NotNull(message = "Дата должна быть указана!")
    private LocalDate date;

    private List<Receipt> receiptList;
}
