package ru.otus.hw.repositories;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;


@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaCommentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllForBook(long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c " +
                        "from Comment c " +
                        "where c.book.id = :book_id",
                Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
           em.persist(comment);
           return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        Optional<Comment> option = findById(id);
        option.ifPresent(em::remove);
    }
}
