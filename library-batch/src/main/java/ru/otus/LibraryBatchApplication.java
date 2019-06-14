package ru.otus;

import org.jline.reader.impl.history.DefaultHistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaRepositories(basePackages = "ru.otus.dao.jpa")
@EnableMongoRepositories(basePackages = "ru.otus.dao.mongo")
public class LibraryBatchApplication {

	@Bean
	public DefaultHistory noSaveHistory() {
		return new DefaultHistory() {
			@Override
			public void save() throws IOException {
				// disable create log file
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryBatchApplication.class, args);
	}
}
