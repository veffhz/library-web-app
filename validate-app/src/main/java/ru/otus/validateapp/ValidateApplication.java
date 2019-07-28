package ru.otus.validateapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableHystrix
@EnableHystrixDashboard
public class ValidateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidateApplication.class, args);
	}
}
