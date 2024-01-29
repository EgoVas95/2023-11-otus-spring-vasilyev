package ru.otus.hw.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;

@RequiredArgsConstructor
@Component
public class CommentCascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
    private final BookRepository bookRepository;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        Object source = event.getSource();
        if (source instanceof Comment) {
           bookRepository.save(((Comment)source).getBook());
        }
    }
}
