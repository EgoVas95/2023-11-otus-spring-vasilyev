package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentCreateDto extends CommentDto {
    public CommentCreateDto(@NotBlank(message = "Book title can't be null")
                            @Size(min = 1, max = 100, message = "Comment text should be with " +
                                    "size from 1 to 100 symbols") String text,
                            @NotNull(message = "Book can't be null") Long bookId) {
        super(null, text, bookId);
    }
}
