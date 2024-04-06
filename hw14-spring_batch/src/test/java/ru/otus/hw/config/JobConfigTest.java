package ru.otus.hw.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.jpa.JpaAuthor;
import ru.otus.hw.models.jpa.JpaBook;
import ru.otus.hw.models.jpa.JpaComment;
import ru.otus.hw.models.jpa.JpaGenre;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoComment;
import ru.otus.hw.models.mongo.MongoGenre;
import ru.otus.hw.processors.AuthorProcessor;
import ru.otus.hw.processors.BookProcessor;
import ru.otus.hw.processors.CommentProcessor;
import ru.otus.hw.processors.GenreProcessor;
import ru.otus.hw.repositories.jpa.JpaAuthorRepository;
import ru.otus.hw.repositories.jpa.JpaBookRepository;
import ru.otus.hw.repositories.jpa.JpaGenreRepository;
import ru.otus.hw.repositories.mongo.MongoAuthorRepository;
import ru.otus.hw.repositories.mongo.MongoBookRepository;
import ru.otus.hw.repositories.mongo.MongoCommentRepository;
import ru.otus.hw.repositories.mongo.MongoGenreRepository;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.config.JobConfig.IMPORT_FROM_DATABASE_JOB_NAME;
import static ru.otus.hw.config.JobConfig.MILLIS_PARAM_NAME;

@SpringBootTest
@AutoConfigureDataMongo
@SpringBatchTest
class JobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoBookRepository bookRepository;

    @Autowired
    private MongoGenreRepository genreRepository;

    @Autowired
    private MongoAuthorRepository authorRepository;

    @Autowired
    private MongoCommentRepository commentRepository;



    @Autowired
    private JpaBookRepository jpaBookRepository;

    @Autowired
    private JpaGenreRepository jpaGenreRepository;

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    @BeforeEach
    void setUp() {
        jobRepositoryTestUtils.removeJobExecutions();
        for (String name : mongoTemplate.getCollectionNames()) {
            mongoTemplate.dropCollection(name);
        }
    }

    @Test
    @DisplayName("Тест выгрузки записей в MongoDB")
    void importFromDatabaseJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_FROM_DATABASE_JOB_NAME);

        mongoTemplate.getCollection("books");

        JobParameters parameters = new JobParametersBuilder()
                .addLong(MILLIS_PARAM_NAME, System.currentTimeMillis())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        assertThat(bookRepository.findAll())
                .isNotEmpty()
                .usingRecursiveComparison()
                .isEqualTo(jpaBookRepository.findAll().stream()
                        .map(this::process).toList());

        assertThat(commentRepository.findAll())
                .isNotEmpty()
                .usingRecursiveComparison()
                .isEqualTo(getExampleCommentList());

        assertThat(authorRepository.findAll())
                .isNotEmpty()
                .usingRecursiveComparison()
                .isEqualTo(StreamSupport.stream(
                        jpaAuthorRepository.findAll(
                                Sort.unsorted()).spliterator(), false)
                        .map(this::process).toList());

        assertThat(genreRepository.findAll())
                .isNotEmpty()
                .usingRecursiveComparison()
                .isEqualTo(StreamSupport.stream(jpaGenreRepository.findAll(
                        Sort.unsorted()).spliterator(), false)
                        .map(this::process).toList());
    }

    private MongoBook process(JpaBook book) {
        BookProcessor processor = new BookProcessor();
        try {
            return processor.process(book);
        } catch (Exception ex) {
            return null;
        }
    }

    private MongoComment process(JpaComment comment) {
        CommentProcessor processor = new CommentProcessor();
        try {
            return processor.process(comment);
        } catch (Exception ex) {
            return null;
        }
    }

    private MongoAuthor process(JpaAuthor author) {
        AuthorProcessor processor = new AuthorProcessor();
        try {
            return processor.process(author);
        } catch (Exception ex) {
            return null;
        }
    }

    private MongoGenre process(JpaGenre genre) {
        GenreProcessor processor = new GenreProcessor();
        try {
            return processor.process(genre);
        } catch (Exception ex) {
            return null;
        }
    }

    private List<MongoComment> getExampleCommentList() {
        JpaBook book1 = new JpaBook(1L, "BookTitle_1",
                new JpaAuthor(1L, "Author_1"),
                new JpaGenre(1L, "Genre_1"));
        JpaBook book2 = new JpaBook(2L, "BookTitle_2",
                new JpaAuthor(2L, "Author_2"),
                new JpaGenre(2L, "Genre_2"));
        JpaBook book3 = new JpaBook(3L, "BookTitle_3",
                new JpaAuthor(3L, "Author_3"),
                new JpaGenre(3L, "Genre_3"));

        return Stream.of(new JpaComment(1L, "Book_1_Comment_1", book1),
                        new JpaComment(2L, "Book_1_Comment_2", book1),
                        new JpaComment(3L, "Book_1_Comment_3", book1),
                        new JpaComment(4L, "Book_2_Comment_1", book2),
                        new JpaComment(5L, "Book_3_Comment_1", book3))
                .map(this::process).toList();
    }
}
