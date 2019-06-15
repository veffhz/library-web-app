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

import ru.otus.dao.jpa.GenreJpaRepository;
import ru.otus.domain.Genre;

import java.util.Collections;

@Slf4j
@Configuration
public class GenreBatchConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final GenreJpaRepository genreRepository;

    @Autowired
    public GenreBatchConfig(StepBuilderFactory stepBuilderFactory, GenreJpaRepository genreRepository) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.genreRepository = genreRepository;
    }

    @Bean(name = "genreReader")
    public RepositoryItemReader<Genre> genreReader() {
        RepositoryItemReader<Genre> reader = new RepositoryItemReader<>();
        reader.setRepository(genreRepository);
        reader.setMethodName("findAll");
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        reader.setPageSize(10000);
        return reader;
    }

    @Bean(name = "genreWriter")
    public MongoItemWriter<Genre> genreWriter(MongoTemplate mongoTemplate) {
        MongoItemWriter<Genre> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("genres");
        return writer;
    }

    @Bean
    public Step genreJpaToMongoStep(MongoItemWriter<Genre> genreWriter) {
        return stepBuilderFactory.get("genreJpaToMongoStep")
                .<Genre, Genre> chunk(10)
                .reader(genreReader())
                .processor((ItemProcessor<Genre, Genre>) genre -> {
                    log.info("Migrate {}", genre);
                    return new Genre(genre.getGenreName());
                })
                .writer(genreWriter)
                .build();
    }

}
