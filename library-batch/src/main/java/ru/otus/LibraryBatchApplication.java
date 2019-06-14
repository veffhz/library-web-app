package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import org.h2.tools.Console;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.SQLException;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaRepositories(basePackages = "ru.otus.dao.jpa")
@EnableMongoRepositories(basePackages = "ru.otus.dao.mongo")
public class LibraryBatchApplication {

	public static void main(String[] args) throws SQLException {
		ConfigurableApplicationContext run = SpringApplication.run(LibraryBatchApplication.class, args);

		CsvParser csvParser = run.getBean(CsvParser.class);
		csvParser.parse();

		Console.main(args);
	}
}
