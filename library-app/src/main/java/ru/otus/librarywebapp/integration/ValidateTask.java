package ru.otus.librarywebapp.integration;

import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;

import ru.otus.librarywebapp.dao.BookRepository;

import java.util.concurrent.atomic.AtomicInteger;

public class ValidateTask {

    private final AtomicInteger counter = new AtomicInteger();

    private final IntegrationService integrationService;

    private final BookRepository bookRepository;

    public ValidateTask(IntegrationService integrationService, BookRepository bookRepository) {
        this.integrationService = integrationService;
        this.bookRepository = bookRepository;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 30000)
    void execute() {
        bookRepository.findAll(PageRequest.of(counter.getAndIncrement(), 10))
                .collectList().doOnNext(integrationService::process).subscribe();

    }
}
