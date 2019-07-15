package ru.otus.librarywebapp.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CustomHealthIndicator implements ReactiveHealthIndicator {

    private final LocalDateTime localDateTime = LocalDateTime.now();

    @Override
    public Mono<Health> health() {
        return checkUptimeServiceHealth().onErrorResume(
                ex -> Mono.just(new Health.Builder().down(ex).build())
        );
    }

    private Mono<Health> checkUptimeServiceHealth() {
        return Mono.just(new Health.Builder().up().withDetail("uptime",
                Duration.between(localDateTime, LocalDateTime.now()).getSeconds()).build());
    }
}
