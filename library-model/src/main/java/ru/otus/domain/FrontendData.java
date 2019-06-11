package ru.otus.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FrontendData {

    private List<Author> authors;
    private List<Genre> genres;
    private List<Book> books;
    private List<Comment> comments;

    public FrontendData withAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    public FrontendData withGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public FrontendData withBooks(List<Book> books) {
        this.books = books;
        return this;
    }

    public FrontendData withComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

}
