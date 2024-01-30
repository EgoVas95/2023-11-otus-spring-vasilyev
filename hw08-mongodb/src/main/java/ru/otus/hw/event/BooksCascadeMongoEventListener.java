package ru.otus.hw.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@RequiredArgsConstructor
@Component
public class BooksCascadeMongoEventListener extends AbstractMongoEventListener<Object> {

    private final CommentRepository commentRepository;

    private final GenreRepository genreRepository;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        Object source = event.getSource();
        if (source instanceof Book) {
            genreRepository.save(((Book)source).getGenre());
        }
    }


    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Object> event) {
        if ("books".equalsIgnoreCase(event.getCollectionName())) {
            String bookId = String.valueOf(event.getSource().get("_id"));
            commentRepository.deleteAll(commentRepository.findAllByBookId(bookId));
        }
    }

}
