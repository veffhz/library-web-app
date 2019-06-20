package ru.otus.config.mongo.entity;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import ru.otus.dao.jpa.AuthorJpaRepository;

import ru.otus.domain.Author;

import java.util.Collections;

@Slf4j
@Configuration
public class AuthorBatchConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final AuthorJpaRepository authorRepository;

    @Autowired
    public AuthorBatchConfig(StepBuilderFactory stepBuilderFactory, AuthorJpaRepository authorRepository) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.authorRepository = authorRepository;
    }

    @Bean(name = "authorReader")
    public RepositoryItemReader<Author> authorReader() {
        RepositoryItemReader<Author> reader = new RepositoryItemReader<>();
        reader.setRepository(authorRepository);
        reader.setMethodName(authorRepository.FIND_ALL_METHOD);
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        reader.setPageSize(10000);
        return reader;
    }

    @Bean(name = "authorWriter")
    public MongoItemWriter<Author> authorWriter(MongoTemplate mongoTemplate) {
        MongoItemWriter<Author> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("authors");
        return writer;
    }

    @Bean(name = "authorProcessor")
    public ItemProcessor<Author, Author> authorProcessor() {
        return author -> {
            log.info("Migrate {}", author);
            return new Author(author.getFirstName(), author.getBirthDate(), author.getLastName());
        };
    }

    @Bean
    public Step authorJpaToMongoStep(MongoTemplate mongoTemplate) {
        return stepBuilderFactory.get("authorJpaToMongoStep")
                .<Author, Author> chunk(10)
                .reader(authorReader())
                .processor(authorProcessor())
                .writer(authorWriter(mongoTemplate))
                .build();
    }

}
