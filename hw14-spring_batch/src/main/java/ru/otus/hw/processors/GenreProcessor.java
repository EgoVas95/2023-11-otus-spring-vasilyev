package ru.otus.hw.processors;

import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import ru.otus.hw.models.jpa.JpaGenre;
import ru.otus.hw.models.mongo.MongoGenre;

public class GenreProcessor implements ItemProcessor<JpaGenre, MongoGenre> {

    @Override
    public MongoGenre process(@Nonnull JpaGenre item) throws Exception {
        return new MongoGenre(null, item.getName());
    }
}
