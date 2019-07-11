package ru.otus.librarywebapp.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Book;

import java.util.List;

@MessagingGateway
public interface IntegrationService {
    @Gateway(requestChannel = "bookInChannel", replyChannel = "bookOutChannel")
    Mono<List<Book>> process(List<Book> book);
}
