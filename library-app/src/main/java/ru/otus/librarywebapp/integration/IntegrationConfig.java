package ru.otus.librarywebapp.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mongodb.inbound.MongoDbMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;

import ru.otus.domain.Book;
import ru.otus.librarywebapp.service.BookValidateService;

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

//    @Bean
//    public MessageSource<Object> mongoMessageSource(MongoDbFactory mongo) {
//        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongo, new LiteralExpression("{'processed' : false}"));
//        messageSource.setExpectSingleResult(true);
//        messageSource.setEntityClass(Book.class);
//        messageSource.setCollectionNameExpression(new LiteralExpression("books"));
//        return messageSource;
//    }

//    @Bean
//    public IntegrationFlow flow(MongoDbFactory mongo, BookValidateService bookValidateService) {
//        return IntegrationFlows.from(mongoMessageSource(mongo))
//                .split()
//                .filter((Book book) -> book.getAuthor().isAvailable())
//                .transform(Book.class, bookValidateService::validate)
//                .aggregate()
//                .channel("bookOutChannel")
//                .get();
//    }

    @Bean
    public IntegrationFlow flow(BookValidateService bookValidateService) {
        return IntegrationFlows.from("bookInChannel")
                .split()
                //.filter((Book book) -> book.getAuthor().isAvailable())
                .transform(Book.class, bookValidateService::validate)
                .aggregate()
                .channel("bookOutChannel")
                .get();
    }
}
