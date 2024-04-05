package ru.otus.hw.processors;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.hw.models.jpa.JpaComment;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoComment;

public class CommentProcessor implements ItemProcessor<JpaComment, MongoComment> {

    @Override
    public MongoComment process(JpaComment item) throws Exception {
        BookProcessor processor = new BookProcessor();
        return new MongoComment(null, item.getText(), null);
    }
}
