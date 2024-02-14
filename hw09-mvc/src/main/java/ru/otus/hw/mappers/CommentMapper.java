package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final BookMapper bookMapper;

    public Comment toModel(CommentDto dto) {
        return new Comment(dto.getId(), dto.getText(),
                bookMapper.toModel(new BookDto(
                        dto.getBookId(), null, null, null)));
    }

    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(),
                comment.getBook().getId());
    }
}
