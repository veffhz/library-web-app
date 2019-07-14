package ru.otus.librarywebapp.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.otus.domain.Book;

import java.util.List;

@MessagingGateway
public interface IntegrationService {
    @Gateway(requestChannel = "bookInChannel", replyChannel = "storeChannel")
    void process(List<Book> book);
}
