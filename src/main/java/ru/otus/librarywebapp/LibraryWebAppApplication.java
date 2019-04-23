package ru.otus.librarywebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LibraryWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryWebAppApplication.class, args);
	}

}
