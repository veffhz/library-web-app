package ru.otus.librarywebapp.integration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.client.RestTemplate;

import ru.otus.domain.AdditionalData;
import ru.otus.domain.Book;

import ru.otus.librarywebapp.service.BookValidateService;

import java.time.Duration;
import java.util.concurrent.Executors;

@Configuration
public class IntegrationConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public QueueChannel bookInChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean (name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(10).get() ;
    }

    @Bean
    @ServiceActivator(inputChannel = "storeChannel")
    public MessageHandler mongoOutboundAdapter(MongoTemplate mongoTemplate) {
        MongoDbStoringMessageHandler adapter = new MongoDbStoringMessageHandler(mongoTemplate);
        adapter.setCollectionNameExpression(new LiteralExpression("additionalData"));
        return adapter;
    }

    @Bean
    public IntegrationFlow flow(BookValidateService bookValidateService) {
        return IntegrationFlows.from("bookInChannel")
                .log()
                .split()
                .channel(c -> c.executor(Executors.newCachedThreadPool()))
                .filter((Book book) -> book.getAuthor().isAvailable())
                .transform(Book.class, bookValidateService::validate)
                .filter(AdditionalData::isNotEmpty)
                .log()
                .channel("storeChannel")
                .get();
    }
}
