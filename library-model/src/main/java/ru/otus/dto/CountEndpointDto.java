package ru.otus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountEndpointDto {

    private Long authors;
    private Long genres;
    private Long books;
    private Long comments;

    public CountEndpointDto withAuthors(Long authors) {
        this.authors = authors;
        return this;
    }

    public CountEndpointDto withGenres(Long genres) {
        this.genres = genres;
        return this;
    }

    public CountEndpointDto withBooks(Long books) {
        this.books = books;
        return this;
    }

    public CountEndpointDto withComments(Long comments) {
        this.comments = comments;
        return this;
    }

}
