package ru.otus.librarywebapp.config;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MongoBeeConfig {

    private final MongoClient mongo;

    @Autowired
    public MongoBeeConfig(MongoClient mongo) {
        this.mongo = mongo;
    }

    @Bean
    public Mongobee mongobee(Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName("library");
        runner.setChangeLogsScanPackage(DatabaseChangelog.class.getPackage().getName());
        runner.setSpringEnvironment(environment);
        return runner;
    }

}
