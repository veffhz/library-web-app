package ru.otus.librarywebapp.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.otus.dto.AuthorDto;
import ru.otus.dto.FrontendDto;

import ru.otus.dto.GenreDto;
import ru.otus.librarywebapp.rest.BookApi;
import ru.otus.librarywebapp.rest.CommentApi;

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
    public String main(Model model) {
        log.info("get /");
        FrontendDto frontendData = new FrontendDto()
                .withAuthors(new AuthorDto(authorService.getAll()))
                .withGenres(new GenreDto(genreService.getAll()))
                .withBooks(bookService.getAll(BookApi.BOOK_PAGE_REQUEST))
                .withComments(commentService.getAll(CommentApi.COMMENTS_PAGE_REQUEST));
        model.addAttribute("frontendData", frontendData);
        return "index";
    }

}
