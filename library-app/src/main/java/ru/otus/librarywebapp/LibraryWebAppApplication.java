package ru.otus.librarywebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.integration.IntegrationService;
import ru.otus.librarywebapp.integration.ValidateTask;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
@EnableReactiveMongoRepositories
@EnableIntegration
@IntegrationComponentScan
public class LibraryWebAppApplication {

	@Bean
	@ConditionalOnProperty(prefix = "validate-service", value = "enabled", havingValue = "true")
	public ValidateTask validateTask(IntegrationService integrationService, BookRepository bookRepository) {
		return new ValidateTask(integrationService, bookRepository);
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryWebAppApplication.class, args);
	}
}
