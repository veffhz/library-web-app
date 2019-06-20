package ru.otus.config.csv;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

import ru.otus.domain.*;

import java.time.LocalDate;

@Slf4j
public class LibraryItemProcessor implements ItemProcessor<LibraryDto, LibraryItem> {

    private static final String N_A = "n/a";
    private LocalDate LocalDateMIN = LocalDate.of(1900, 1, 1);

    @Override
    public LibraryItem process(final LibraryDto libraryDto) {

        String bookName = libraryDto.getBookName();
        String strAuthor = libraryDto.getAuthor();
        String year = libraryDto.getYear();
        String strGenre = libraryDto.getGenre();
        String isbn = libraryDto.getIsbn();

        Author author = parseAuthor(strAuthor);
        Genre genre = parseGenre(strGenre);

        LibraryItem transformedDto =
                new LibraryItem()
                        .withAuthor(author)
                        .withGenre(genre)
                        .withBook(parseBook(bookName, year, isbn, author, genre));

        if (log.isDebugEnabled()) {
            log.debug("Converting [{}] into [{}]", libraryDto, transformedDto);
        }

        return transformedDto;
    }

    private Author parseAuthor(String strAuthor) {
        String author = strAuthor.trim();
        if (StringUtils.isEmpty(author)) {
            return null;
        }

        String[] fio = author.split("\\s");

        if (fio.length > 1) {
            return new Author(fio[1].trim(), LocalDateMIN, fio[0].trim());
        } else {
            return new Author(N_A, LocalDateMIN, fio[0].trim());
        }
    }

    private Genre parseGenre(String strGenre) {
        String genre = strGenre.trim();
        return new Genre(StringUtils.capitalize(genre.toLowerCase()));
    }

    private Book parseBook(String strBookName, String year, String isbn, Author author, Genre genre) {
        String bookName = strBookName.trim();
        LocalDate date = LocalDate.of(Integer.parseInt(year), 1, 1);
        return new Book(author, genre, bookName, date, "RU", N_A, N_A, isbn);
    }

}