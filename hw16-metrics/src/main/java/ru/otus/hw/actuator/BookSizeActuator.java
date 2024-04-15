package ru.otus.hw.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.BookService;

@Component
@RequiredArgsConstructor
public class BookSizeActuator implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {
        return Health.up()
                .withDetail("message", "book count = %d"
                        .formatted(bookService.findAll().size()))
                .build();
    }
}
