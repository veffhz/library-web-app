package ru.otus.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;

import ru.otus.AppProperties;

import ru.otus.dao.jpa.AuthorJpaRepository;
import ru.otus.dao.jpa.BookJpaRepository;
import ru.otus.dao.jpa.GenreJpaRepository;

import ru.otus.domain.LibraryDto;
import ru.otus.domain.LibraryItem;

@Slf4j
@EnableBatchProcessing
@Configuration
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private final String delimiter;
    private final String csvFile;

    @Autowired
    public BatchConfig(AppProperties properties) {
        this.delimiter = properties.getDelimiter();
        this.csvFile = properties.getCsvFile();
    }

    @Bean
    public FlatFileItemReader<LibraryDto> libraryDtoReader() {
        return new FlatFileItemReaderBuilder<LibraryDto>()
                .name("libraryItemReader")
                .resource(new ClassPathResource(csvFile))
                .delimited()
                .delimiter(delimiter)
                .names(new String[]{"bookName", "author", "year", "genre", "isbn"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<LibraryDto>() {{
                    setTargetType(LibraryDto.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<LibraryDto, LibraryItem> libraryItemProcessor() {
        return new LibraryItemProcessor();
    }

    @Bean
    public LibraryItemWriter writer(AuthorJpaRepository authorRepository, GenreJpaRepository genreRepository, BookJpaRepository bookRepository) {
        LibraryItemWriter jpaItemWriter = new LibraryItemWriter(authorRepository, genreRepository, bookRepository);
        return jpaItemWriter;
    }

    @Bean
    public Step csvFileToDatabaseStep(LibraryItemWriter writer) {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<LibraryDto, LibraryItem>chunk(1)
                .reader(libraryDtoReader())
                .processor(libraryItemProcessor())
                .writer(writer)
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("Начало job");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("Конец job");
            }
        };
    }

    @Bean
    public Job csvFileToDatabaseJob(Step csvFileToDatabaseStep) {
        return jobBuilderFactory.get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(csvFileToDatabaseStep)
                .end()
                .build();
    }

}
