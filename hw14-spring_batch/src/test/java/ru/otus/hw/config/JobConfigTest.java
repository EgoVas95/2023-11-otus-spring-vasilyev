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
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.otus.hw.repositories.jpa.JpaCommentRepository;
import ru.otus.hw.repositories.jpa.JpaGenreRepository;
import ru.otus.hw.repositories.mongo.MongoAuthorRepository;
import ru.otus.hw.repositories.mongo.MongoBookRepository;
import ru.otus.hw.repositories.mongo.MongoCommentRepository;
import ru.otus.hw.repositories.mongo.MongoGenreRepository;

import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.config.JobConfig.IMPORT_FROM_DATABASE_JOB_NAME;
import static ru.otus.hw.config.JobConfig.MILLIS_PARAM_NAME;

@SpringBootTest
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
    private JpaBookRepository jpaBookRepository;

    @Autowired
    private MongoGenreRepository genreRepository;

    @Autowired
    private JpaGenreRepository jpaGenreRepository;

    @Autowired
    private MongoAuthorRepository authorRepository;

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    @Autowired
    private MongoCommentRepository commentRepository;

    @Autowired
    private JpaCommentRepository jpaCommentRepository;

    @Autowired
    BeanFactory beanFactory;

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

        /*assertThat(commentRepository.findAll())
                .isNotEmpty()
                .usingRecursiveComparison()
                .isEqualTo(StreamSupport.stream(jpaCommentRepository.findAll(
                        Sort.unsorted()).spliterator(), false)
                        .map(this::process).toList());*/

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
}
