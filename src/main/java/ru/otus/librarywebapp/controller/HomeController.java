package ru.otus.librarywebapp.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;

import reactor.core.publisher.Mono;

import ru.otus.librarywebapp.domain.*;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

@Slf4j
@Controller
public class HomeController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Autowired
    public HomeController(BookService bookService, AuthorService authorService,
                          GenreService genreService, CommentService commentService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public Rendering main() {
        log.info("get /");

        Mono<FrontendData> frontendData = authorService.getAll().collectList()
                .map(authors -> new FrontendData().withAuthors(authors))
                .zipWith(genreService.getAll().collectList())
                .map(data -> data.getT1().withGenres(data.getT2()))
                .zipWith(bookService.getAll().collectList())
                .map(data -> data.getT1().withBooks(data.getT2()))
                .zipWith(commentService.getAll().collectList())
                .map(data -> data.getT1().withComments(data.getT2()));

        return Rendering.view("index")
                .modelAttribute("frontendData", frontendData)
                .build();
    }

}
