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

import ru.otus.dao.jpa.BookJpaRepository;
import ru.otus.dao.mongo.AuthorRepository;
import ru.otus.dao.mongo.GenreRepository;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Configuration
public class BookBatchConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final BookJpaRepository bookRepository;

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookBatchConfig(StepBuilderFactory stepBuilderFactory, BookJpaRepository bookRepository,
                           AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Bean(name = "bookReader")
    public RepositoryItemReader<Book> bookReader() {
        RepositoryItemReader<Book> reader = new RepositoryItemReader<>();
        reader.setRepository(bookRepository);
        reader.setMethodName(bookRepository.FIND_ALL_METHOD);
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        reader.setPageSize(10000);
        return reader;
    }

    @Bean(name = "bookWriter")
    public MongoItemWriter<Book> bookWriter(MongoTemplate mongoTemplate) {
        MongoItemWriter<Book> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("books");
        return writer;
    }

    @Bean(name = "bookProcessor")
    public ItemProcessor<Book, Book> bookProcessor() {
        return book -> {
            log.info("Migrate {}", book.getBookName());
            Optional<Author> author = authorRepository.findByFirstNameAndLastName(book.getAuthor().getFirstName(),
                    book.getAuthor().getLastName());
            Optional<Genre> genre = genreRepository.findOneByGenreName(book.getGenre().getGenreName());
            return new Book(author.get(), genre.get(),
                    book.getBookName(), book.getPublishDate(),
                    book.getLanguage(), book.getPublishingHouse(),
                    book.getCity(), book.getIsbn());
        };
    }

    @Bean
    public Step bookJpaToMongoStep(MongoTemplate mongoTemplate) {
        return stepBuilderFactory.get("bookJpaToMongoStep")
                .<Book, Book> chunk(10)
                .reader(bookReader())
                .processor(bookProcessor())
                .writer(bookWriter(mongoTemplate))
                .build();
    }

}
