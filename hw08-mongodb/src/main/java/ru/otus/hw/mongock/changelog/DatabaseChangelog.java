package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.CommentRepository;

@ChangeLog
public class DatabaseChangelog {
    private int commentId = 1;

    @ChangeSet(order = "001", id = "dropTables", author = "egovas", runAlways = true)
    public void dropTables(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "addCommentForFirstBook", author = "egovas")
    public void addCommentForFirstBook(CommentRepository commentRepository) {
        final Author bulgakov = new Author("1", "М.А. Булгаков");
        final Genre classic = new Genre("1", "Классика");
        final Book fatalEggs = new Book("1", "Роковые яйца", bulgakov, classic);

        commentRepository.save(new Comment(String.valueOf(commentId++), "Не читал", fatalEggs));
        commentRepository.save(new Comment(String.valueOf(commentId++), "В целом - норм", fatalEggs));
        commentRepository.save(new Comment(String.valueOf(commentId++), "Мне не очень", fatalEggs));
    }

    @ChangeSet(order = "003", id = "addCommentForSecondBook", author = "egovas")
    public void addCommentForSecondBook(CommentRepository commentRepository) {
        final Author strugackie = new Author("2", "Братья Стругацкие");
        final Genre sciFi = new Genre("2", "Научная фантастика");
        final Book baby = new Book("2", "Малыш", strugackie, sciFi);

        commentRepository.save(new Comment(String.valueOf(commentId++), "Ох, люблю этих писателей", baby));
    }

    @ChangeSet(order = "004", id = "addCommentForThirdBook", author = "egovas")
    public void addCommentForThirdBook(CommentRepository commentRepository) {
        final Author uelsh = new Author("3", "Ирвин Уэлш");
        final Genre newWave = new Genre("3", "Нью вейв");
        final Book acidHouse = new Book("3", "Эйсид хаус", uelsh, newWave);

        commentRepository.save(new Comment(String.valueOf(commentId), "Становится жутко после прочтения", acidHouse));
    }

    @ChangeSet(order = "005", id = "addCommentForFourthBook", author = "egovas")
    public void addCommentForFourthBook(CommentRepository commentRepository) {
        final Author bulgakov = new Author("1", "М.А. Булгаков");
        final Genre classic = new Genre("1", "Классика");
        final Book masterAndMargarite = new Book("4", "Мастер и Маргарита", bulgakov, classic);

        commentRepository.save(new Comment(String.valueOf(commentId), "Класс", masterAndMargarite));
    }
}
