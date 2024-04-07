package ru.otus.hw.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FiveOClockActuator implements HealthIndicator {
    @Override
    public Health health() {
        int hour = LocalDateTime.now().getHour();
        if (hour == 17) {
            return Health.down()
                    .withDetail("message", "Five o'clock, tea time!")
                    .withDetail("work_suspended", true)
                    .withDetail("work_hard", false)
                    .build();
        } else {
            return Health.up()
                    .withDetail("message", "Well, we're workin.")
                    .withDetail("work_suspended", false)
                    .withDetail("work_hard", true)
                    .build();
        }
    }
}
