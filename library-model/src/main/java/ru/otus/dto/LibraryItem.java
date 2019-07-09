package ru.otus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

@Data
@NoArgsConstructor
public class LibraryItem {

    private Author author;
    private Genre genre;
    private Book book;

    public LibraryItem withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public LibraryItem withGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public LibraryItem withBook(Book book) {
        this.book = book;
        return this;
    }

}
