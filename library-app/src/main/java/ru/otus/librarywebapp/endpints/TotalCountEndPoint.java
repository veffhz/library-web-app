package ru.otus.librarywebapp.endpints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Mono;

import ru.otus.dto.CountEndpointDto;

import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

@Component
@RestControllerEndpoint(id="totalcounts")
public class TotalCountEndPoint {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Autowired
    public TotalCountEndPoint(BookService bookService, AuthorService authorService,
                              GenreService genreService, CommentService commentService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @GetMapping
    @ResponseBody
    public Mono<CountEndpointDto> totalCount() {
        return authorService.count()
                .map(authors -> new CountEndpointDto().withAuthors(authors))
                .zipWith(genreService.count())
                .map(data -> data.getT1().withGenres(data.getT2()))
                .zipWith(bookService.count())
                .map(data -> data.getT1().withBooks(data.getT2()))
                .zipWith(commentService.count())
                .map(data -> data.getT1().withComments(data.getT2()));
    }

}
