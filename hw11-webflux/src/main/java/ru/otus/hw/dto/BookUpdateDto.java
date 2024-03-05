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
    private String id;

    private String title;

    private String authorId;

    private String genreId;
}
