package ru.otus.hw.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentUpdateDto {
    @NotNull
    private Long id;

    private String text;

    private Long bookId;
}
