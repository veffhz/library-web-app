package ru.otus.config.mongo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfigMigrateMongo {

    private final JobBuilderFactory jobBuilderFactory;

    @Autowired
    public BatchConfigMigrateMongo(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public JobExecutionListener migrateListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("Begin migrate job");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("End migrate job");
            }
        };
    }

    @Bean
    public Job jpaEntitiesToMongoJob(Step authorJpaToMongoStep, Step genreJpaToMongoStep, Step bookJpaToMongoStep) {
        return jobBuilderFactory.get("jpaToMongoJob")
                .incrementer(new RunIdIncrementer())
                .listener(migrateListener())
                .flow(authorJpaToMongoStep)
                .next(genreJpaToMongoStep)
                .next(bookJpaToMongoStep)
                .end()
                .build();
    }

}
