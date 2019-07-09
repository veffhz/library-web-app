package ru.otus.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FrontendDto {

    private AuthorDto authorDto;
    private GenreDto genreDto;
    private BookDto bookDto;
    private CommentDto commentDto;

    public FrontendDto withAuthors(AuthorDto authorDto) {
        this.authorDto = authorDto;
        return this;
    }

    public FrontendDto withGenres(GenreDto genreDto) {
        this.genreDto = genreDto;
        return this;
    }

    public FrontendDto withBooks(BookDto bookDto) {
        this.bookDto = bookDto;
        return this;
    }

    public FrontendDto withComments(CommentDto commentDto) {
        this.commentDto = commentDto;
        return this;
    }

}
