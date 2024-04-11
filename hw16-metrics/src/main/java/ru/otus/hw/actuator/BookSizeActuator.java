package ru.otus.hw.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.hw.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class BookSizeActuator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        return Health.up()
                .withDetail("message", "book count = %d"
                        .formatted(bookRepository.findAll().size()))
                .build();
    }
}
