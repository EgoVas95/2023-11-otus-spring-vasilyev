package ru.otus.hw.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class BookUpdateDto {
    @NotNull
    private Long id;

    private String title;

    private Long authorId;

    private Long genreId;
}
