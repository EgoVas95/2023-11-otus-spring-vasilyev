package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями")
@DataJpaTest
@Import(JpaCommentRepository.class)
public class JpaCommentRepositoryTest {
    @Autowired
    private JpaCommentRepository commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_COMMENT_ID = 1L;

    private static final long BOOK_FIRST_ID = 1L;
    private static final long BOOK_SECOND_ID = 2L;

    private static final String NEW_COMMENT_TEXT = "Test comment";

    @DisplayName("должен добавить новый комментарий к книге с id = 1")
    @Test
    void shouldAddNewComment() {
        Book firstBook = em.find(Book.class, BOOK_FIRST_ID);

        Comment addedComment = new Comment(0L, NEW_COMMENT_TEXT, firstBook);
        em.merge(addedComment);
        commentRepositoryJpa.save(addedComment);
        em.detach(addedComment);
        Comment findComment = em.find(Comment.class, addedComment.getId());

        assertThat(addedComment).usingRecursiveComparison().isEqualTo(findComment);
    }

    @DisplayName("должен изменить данные комментария")
    @Test
    void shouldChangeComment() {
        Book secondBook = em.find(Book.class, BOOK_SECOND_ID);

        var previousComment = em.find(Comment.class, FIRST_COMMENT_ID);
        em.detach(previousComment);

        var updatedComment = commentRepositoryJpa.save(
                new Comment(FIRST_COMMENT_ID, NEW_COMMENT_TEXT, secondBook));
        var findComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(updatedComment).usingRecursiveComparison()
                .isNotEqualTo(previousComment)
                .isEqualTo(findComment);
    }

    @DisplayName("должен находить корректный комментарий по id")
    @Test
    void shouldFindExpectedCommentById() {
        val optionalActualComment = commentRepositoryJpa.findById(FIRST_COMMENT_ID);
        val expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("должен найти все комментарии для книги с id = 1")
    @Test
    void shouldFindAllCommentsForFirstBook() {
        var findComments = commentRepositoryJpa.findAllForBook(BOOK_FIRST_ID);
        var expectedComments = getCommentsForFirstBook();

        assertThat(findComments).containsExactlyElementsOf(expectedComments);
        findComments.forEach(System.out::println);
    }

    private List<Comment> getCommentsForFirstBook() {
        Book book = em.find(Book.class, 1);
        return IntStream.range(1, 4).boxed()
                .map(id -> new Comment(Long.valueOf(id), "Book_%d_Comment_%d".formatted(BOOK_FIRST_ID, id),
                        book))
                .toList();
    }
}
