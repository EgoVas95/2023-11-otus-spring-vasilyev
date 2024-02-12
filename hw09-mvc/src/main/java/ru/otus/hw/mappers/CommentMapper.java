package ru.otus.hw.mappers;

import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

public class CommentMapper {

    public static Comment toDomainObject(CommentDto dto) {
        return new Comment(dto.getId(), dto.getText(),
                BookMapper.toDomainObject(new BookDto(dto.getBookId(), null, null, null)));
    }

    public static CommentDto fromDomainObject(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(),
                comment.getBook().getId());
    }
}
