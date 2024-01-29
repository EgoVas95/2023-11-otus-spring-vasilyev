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
    @ChangeSet(order = "001", id = "dropTables", author = "egovas", runAlways = true)
    public void dropTables(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "fillData", author = "egovas")
    public void fillBookData(CommentRepository commentRepository) {
        int commentId = 1;

        {
            final Author bulgakov = new Author("1", "М.А. Булгаков");
            final Genre classic = new Genre("1", "Классика");
            final Book fatalEggs = new Book("1", "Роковые яйца", bulgakov, classic);

            commentRepository.save(new Comment(String.valueOf(commentId++), "Не читал", fatalEggs));
            commentRepository.save(new Comment(String.valueOf(commentId++), "В целом - норм", fatalEggs));
            commentRepository.save(new Comment(String.valueOf(commentId++), "Мне не очень", fatalEggs));
        }
        {
            final Author strugackie = new Author("2", "Братья Стругацкие");
            final Genre sciFi = new Genre("2", "Научная фантастика");
            final Book baby = new Book("2", "Малыш", strugackie, sciFi);

            commentRepository.save(new Comment(String.valueOf(commentId++), "Ох, люблю этих писателей", baby));
        }
        {
            final Author uelsh = new Author("3", "Ирвин Уэлш");
            final Genre newWave = new Genre("3", "Нью вейв");
            final Book acidHouse = new Book("3", "Эйсид хаус", uelsh, newWave);

            commentRepository.save(new Comment(String.valueOf(commentId), "Становится жутко после прочтения", acidHouse));
        }
    }
}
