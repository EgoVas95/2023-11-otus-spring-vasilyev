package ru.otus.hw.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.exceptions.EntityDeleteException;

@RequiredArgsConstructor
@Component
public class GenreCascadeDeleteMongoEventListener extends AbstractMongoEventListener<Object> {


    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Object> event) {
        if ("genres".equalsIgnoreCase(event.getCollectionName())) {
            String genreName = String.valueOf(event.getSource().get("name"));
            throw new EntityDeleteException("Перед удалением жанра %s удалите все связанные с ним книги!"
                    .formatted(genreName));
        }
    }
}
