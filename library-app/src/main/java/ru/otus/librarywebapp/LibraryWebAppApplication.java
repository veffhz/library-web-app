package ru.otus.librarywebapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.integration.ValidateTask;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
@EnableReactiveMongoRepositories
@EnableHystrix
public class LibraryWebAppApplication {

    @Bean
    @ConditionalOnProperty(prefix = "validate-app", value = "enabled", havingValue = "true")
    public ValidateTask task(@Value("${validate-app.url}") String url, BookRepository bookRepository) {
        return new ValidateTask(url, bookRepository);
    }

	public static void main(String[] args) {
		SpringApplication.run(LibraryWebAppApplication.class, args);
	}
}
