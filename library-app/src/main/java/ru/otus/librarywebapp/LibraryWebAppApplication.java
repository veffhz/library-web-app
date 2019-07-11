package ru.otus.librarywebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ru.otus.dto.BookDto;
import ru.otus.librarywebapp.integration.IntegrationService;
import ru.otus.librarywebapp.service.BookService;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
@EnableReactiveMongoRepositories
@EnableIntegration
@IntegrationComponentScan
public class LibraryWebAppApplication {

	private final AtomicInteger counter = new AtomicInteger();

	@Autowired
	private BookService bookService;

	@Autowired
	private IntegrationService integrationService;

	@Scheduled(initialDelay = 1000, fixedRate = 60000)
	void init() {
		bookService.getAll(PageRequest.of(counter.getAndIncrement(), 10))
				.map(BookDto::getBooks).flatMap(ls -> integrationService.process(ls))
				.subscribe();

		//Mono.fromCallable(book -> integrationService.process(books))
		//		.subscribeOn(Schedulers.elastic());
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryWebAppApplication.class, args);
	}
}
