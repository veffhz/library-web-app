package ru.otus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.List;

@Data
@NoArgsConstructor
public class FrontendDto {

    private List<Author> authors;
    private GenreDto genres;
    private List<Book> books;
    private List<Comment> comments;

    public FrontendDto withAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    public FrontendDto withGenres(GenreDto genres) {
        this.genres = genres;
        return this;
    }

    public FrontendDto withBooks(List<Book> books) {
        this.books = books;
        return this;
    }

    public FrontendDto withComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

}
