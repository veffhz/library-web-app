package ru.otus.librarywebapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@NoArgsConstructor
@ToString
@Document(collection = "books")
@EqualsAndHashCode
public class Book {

    @Id
    private String id;
    @DBRef
    private Author author;
    @DBRef
    private Genre genre;
    @Indexed
    private String bookName;
    private Date publishDate;
    private String language;
    private String publishingHouse;
    private String city;
    private String isbn;

    public Book(Author author, Genre genre, String bookName,
                Date publishDate, String language,
                String publishingHouse, String city, String isbn) {
        this.author = author;
        this.genre = genre;
        this.bookName = bookName;
        this.publishDate = publishDate;
        this.language = language;
        this.publishingHouse = publishingHouse;
        this.city = city;
        this.isbn = isbn;
    }

}
