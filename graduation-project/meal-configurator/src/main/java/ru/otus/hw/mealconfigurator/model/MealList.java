package ru.otus.hw.mealconfigurator.model;

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
@Document(collection = "meal-list")
public class MealList {
    @Id
    private String id;

    private String userSub;

    private List<String> dayByStringList;
}
