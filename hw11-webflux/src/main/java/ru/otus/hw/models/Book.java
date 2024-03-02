package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Author author;

    @NotNull
    private Genre genre;

}
