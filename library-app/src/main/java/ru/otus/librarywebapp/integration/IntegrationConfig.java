package ru.otus.librarywebapp.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

import ru.otus.domain.Book;

import ru.otus.librarywebapp.service.BookValidateService;

import java.util.concurrent.Executors;

@Configuration
public class IntegrationConfig {
    @Bean
    public QueueChannel bookInChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel bookOutChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean (name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(10).get() ;
    }

    @Bean
    public IntegrationFlow flow(BookValidateService bookValidateService) {
        return IntegrationFlows.from("bookInChannel")
                .log()
                .split()
                .channel(c -> c.executor(Executors.newCachedThreadPool()))
                //.filter((Book book) -> book.getAuthor().isAvailable())
                .transform(Book.class, bookValidateService::validate)
                .aggregate()
                .log()
                .channel("bookOutChannel")
                .get();
    }
}
