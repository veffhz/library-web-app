package ru.otus.librarywebapp.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private final LocalDateTime localDateTime = LocalDateTime.now();

    @Override
    public Health health() {
        return checkUptimeServiceHealth();
    }

    private Health checkUptimeServiceHealth() {
        return new Health.Builder().up().withDetail("uptime",
                Duration.between(localDateTime, LocalDateTime.now()).getSeconds()).build();
    }
}
