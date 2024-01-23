package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Nonnull
    @EntityGraph("comment-entity-graph")
    List<Comment> findAll();

    List<Comment> findAllByBookId(long bookID);
}
