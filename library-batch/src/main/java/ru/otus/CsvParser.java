package ru.otus;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.dao.jpa.AuthorJpaRepository;
import ru.otus.dao.jpa.BookJpaRepository;
import ru.otus.dao.jpa.GenreJpaRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.domain.LibraryItem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Log
@Service
public class CsvParser {

    private LocalDate LocalDateMIN = LocalDate.of(1970, 1, 1);
    private Author noAuthor = new Author("Неизвестен", LocalDateMIN, "Неизвестен");

    private final String separator;
    private final String csvFile;
    private final Random random = new Random();

    private final AuthorJpaRepository authorRepository;
    private final GenreJpaRepository genreRepository;
    private final BookJpaRepository bookRepository;

    @Autowired
    public CsvParser(AppProperties properties,
                     AuthorJpaRepository authorRepository,
                     GenreJpaRepository genreRepository,
                     BookJpaRepository bookRepository) {
        this.separator = properties.getSeparator();
        this.csvFile = properties.getCsvFile();
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<LibraryItem> parse() {
        List<LibraryItem> items = new ArrayList<>();
        List<String> fileLines;

        try {
            URL resource = getClass().getResource(csvFile);
            URI uri = resource.toURI();
            fileLines = Files.readAllLines(Paths.get(uri));

            //skip first line
            for (int i = 1; i < fileLines.size(); i++) {
                String line = fileLines.get(i);
                String[] splitedText = line.split(separator);
                ArrayList<String> columnList = new ArrayList<>(Arrays.asList(splitedText));

                String id = StringUtils.isEmpty(columnList.get(0)) ? "" : columnList.get(0).trim();
                String name = StringUtils.isEmpty(columnList.get(0)) ? "" : columnList.get(1).trim();
                String author = StringUtils.isEmpty(columnList.get(0)) ? "" : columnList.get(2).trim();
                String year = StringUtils.isEmpty(columnList.get(0)) ? "" : columnList.get(3).trim();
                String type = StringUtils.isEmpty(columnList.get(0)) ? "" : columnList.get(4).trim();
                String genre = StringUtils.isEmpty(columnList.get(0)) ? "" : columnList.get(5).trim();
                String age = StringUtils.isEmpty(columnList.get(0)) ? "" : columnList.get(6).trim();
                String isbn = StringUtils.isEmpty(columnList.get(0)) ? "" : columnList.get(7).trim();

                LibraryItem item = new LibraryItem();

                item = item.withAuthor(getAuthor(author));
                item = item.withGenre(getGenre(StringUtils.capitalize(genre.toLowerCase())));
                item = item.withBook(getBook(name, year, isbn, item));

                items.add(item);
            }
        } catch (IndexOutOfBoundsException e) {
            log.severe(e.getMessage());
        } catch (URISyntaxException | IOException e) {
            log.severe(e.getMessage());
        }

        return items;
    }

    private Book getBook(String name, String year, String isbn, LibraryItem item) {
        Book book = prepareBook(name, year, isbn, item);

        List<Book> books = bookRepository.findByBookName(book.getBookName());
        if (books.size() == 1) {
            return books.get(0);
        } else if (books.size() > 1) {
            throw new RuntimeException("books " + books);
        } else {
            return bookRepository.save(book);
        }
    }

    private Book prepareBook(String name, String year, String isbn, LibraryItem item) {

        if ("-".equals(isbn)) {
            isbn = String.format("%d-%d%d-%d%d%d-2", random(), random100(),
                    random100(), random100(), random100(), random());
        }

        LocalDate date = LocalDateMIN;

        if (!"б/г".equals(year)) {
            date = LocalDate.of(Integer.parseInt(year), 1, 1);
        }

        return new Book(item.getAuthor(), item.getGenre(), name, date, "ru", "-", "-", isbn);
    }

    private Genre getGenre(String genreName) {
        List<Genre> genres = genreRepository.findByGenreName(genreName);
        if (genres.size() == 1) {
            return genres.get(0);
        } else if (genres.size() > 1) {
            throw new RuntimeException("genres " + genres);
        } else {
            return genreRepository.save(new Genre(genreName));
        }
    }

    private Author prepareAuthor(String strAuthor) {
        if (StringUtils.isEmpty(strAuthor)){
            return noAuthor;
        }

        String[] fio = strAuthor.split(" ");

        if (fio.length > 1) {
            return new Author(fio[1], LocalDateMIN, fio[0]);
        } else {
            return new Author("Неизвестен", LocalDateMIN, fio[0]);
        }
    }

    private Author getAuthor(String strAuthor) {
        Author author = prepareAuthor(strAuthor);

        List<Author> authors = authorRepository.findByLastName(author.getLastName());
        if (authors.size() == 1) {
            return authors.get(0);
        } else if (authors.size() > 1) {
            throw new RuntimeException("authors " + authors);
        } else {
            return authorRepository.save(author);
        }
    }

    private int random() {
        return random.nextInt(9);
    }

    private int random100() {
        return random.nextInt(100);
    }

}
