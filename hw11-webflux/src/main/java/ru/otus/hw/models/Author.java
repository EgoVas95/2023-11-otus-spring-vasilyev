package ru.otus.hw.models;

import org.jetbrains.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    private Long id;

    @NotNull
    private String fullName;
}
