package ru.otus.librarywebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.web.client.RestTemplate;
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

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
		return new RestTemplate(clientHttpRequestFactory);
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(9000);
		clientHttpRequestFactory.setReadTimeout(9000);
		clientHttpRequestFactory.setConnectionRequestTimeout(9000);
		return clientHttpRequestFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryWebAppApplication.class, args);
	}
}
