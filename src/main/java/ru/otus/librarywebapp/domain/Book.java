package ru.otus.librarywebapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    @DBRef
    private Author author;
    @DBRef
    private Genre genre;
    @NotBlank
    @Indexed
    private String bookName;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate publishDate;
    @NotBlank
    private String language;
    @NotBlank
    private String publishingHouse;
    @NotBlank
    private String city;
    @NotBlank
    private String isbn;

    public Book(Author author, Genre genre, String bookName,
                LocalDate publishDate, String language,
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
