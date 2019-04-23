package ru.otus.librarywebapp.testconfig;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MongoBeeTestConfig {

    private final MongoClient mongo;

    @Autowired
    public MongoBeeTestConfig(MongoClient mongo) {
        this.mongo = mongo;
    }

    @Bean
    public Mongobee mongobee(Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName("test");
        runner.setChangeLogsScanPackage(TestDatabaseChangelog.class.getPackage().getName());
        runner.setSpringEnvironment(environment);
        return runner;
    }

}
