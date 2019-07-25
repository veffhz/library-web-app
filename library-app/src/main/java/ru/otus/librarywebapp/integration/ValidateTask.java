package ru.otus.librarywebapp.integration;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.scheduler.Schedulers;

import ru.otus.dto.BookDto;
import ru.otus.librarywebapp.dao.BookRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ValidateTask {

    private final AtomicInteger counter = new AtomicInteger();
    private final String url;
    private final BookRepository bookRepository;

    public ValidateTask(String url, BookRepository bookRepository) {
        this.url = url;
        this.bookRepository = bookRepository;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 30000)
    void execute() {
//        bookRepository.findAll(PageRequest.of(counter.getAndIncrement(), 10))
//                .collectList().publishOn(Schedulers.elastic())
//                .doOnNext(list -> {
//                    log.info("processing next {}", counter);
//                    WebClient.create(url).post().accept(MediaType.APPLICATION_JSON_UTF8).
//                            body(BodyInserters.fromObject(new BookDto(list, counter.intValue(), 0L))).
//                            retrieve().onStatus(HttpStatus::isError,
//                            response -> response.bodyToMono(String.class).map(RuntimeException::new))
//                            .bodyToMono(String.class).subscribe(result -> log.info("response {}", result));
//                }).subscribe();
    }
}
