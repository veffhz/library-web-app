package ru.otus.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemWriter;

import ru.otus.dao.jpa.AuthorJpaRepository;
import ru.otus.dao.jpa.BookJpaRepository;
import ru.otus.dao.jpa.GenreJpaRepository;

import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.domain.LibraryItem;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class LibraryItemWriter implements ItemWriter<LibraryItem> {

    private final AuthorJpaRepository authorRepository;
    private final GenreJpaRepository genreRepository;
    private final BookJpaRepository bookRepository;

    public LibraryItemWriter(AuthorJpaRepository authorRepository, GenreJpaRepository genreRepository, BookJpaRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void write(List<? extends LibraryItem> items) {
        if (log.isDebugEnabled()) {
            log.debug("Writing to JPA with " + items.size() * 3 + " items.");
        }

        if (!items.isEmpty()) {
            long mergeCount = 0L;
            Iterator<? extends LibraryItem> iterator = items.iterator();

            while(iterator.hasNext()) {
                LibraryItem item = iterator.next();
                if ((Objects.isNull(item.getAuthor()))) {
                    Author author = authorRepository.getOne("1");
                    item.getBook().setAuthor(author);
                }
                else {
                    Optional<Author> author = authorRepository
                            .findByFirstNameAndLastName(item.getAuthor().getFirstName(),
                                    item.getAuthor().getLastName());
                    if (author.isPresent()) {
                        item.getBook().setAuthor(author.get());
                    } else {
                        Author saveAuthor = authorRepository.save(item.getAuthor());
                        item.getBook().setAuthor(saveAuthor);
                        ++mergeCount;
                    }
                }

                if (Objects.nonNull(item.getGenre())) {
                    Optional<Genre> genre = genreRepository.findOneByGenreName(item.getGenre().getGenreName());
                    if (genre.isPresent()) {
                        item.getBook().setGenre(genre.get());
                    } else {
                        Genre saveGenre = genreRepository.save(item.getGenre());
                        item.getBook().setGenre(saveGenre);
                        ++mergeCount;
                    }
                }

                if (Objects.nonNull(item.getBook())) {
                    bookRepository.save(item.getBook());
                    ++mergeCount;
                }
            }

            if (log.isDebugEnabled()) {
                log.debug(mergeCount + " entities added.");
                log.debug((long)items.size() * 3 - mergeCount + " entities found.");
            }
        }

    }

}
