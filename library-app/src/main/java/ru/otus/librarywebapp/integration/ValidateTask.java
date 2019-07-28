package ru.otus.librarywebapp.integration;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

import ru.otus.dto.BookDto;

import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.exception.ValidateException;
import ru.otus.librarywebapp.rest.BookApi;

@Slf4j
public class ValidateTask {

    private final AtomicInteger counter = new AtomicInteger();
    private final String url;
    private final BookRepository bookRepository;

    public ValidateTask(String url, BookRepository bookRepository) {
        this.url = url;
        this.bookRepository = bookRepository;
    }

    @Scheduled(initialDelay = 5000, fixedRate = 30000)
    void execute() {
        bookRepository.findAll(PageRequest.of(counter.getAndIncrement(), BookApi.BOOKS_PER_PAGE))
                .collectList().publishOn(Schedulers.elastic())
                .doOnNext(list -> {
                    log.info("processing #{} next {}", counter, BookApi.BOOKS_PER_PAGE);
                    WebClient.create(url).post().accept(MediaType.APPLICATION_JSON_UTF8).
                            body(BodyInserters.fromObject(new BookDto(list, counter.intValue(), 0L))).
                            exchange().subscribe(result -> {
                        if (result.statusCode().isError()) {
                            throw new ValidateException(result.statusCode().getReasonPhrase());
                        } else {
                            log.info("response code {}", result.statusCode());
                        }
                    });
                })
                .subscribe();
    }
}
