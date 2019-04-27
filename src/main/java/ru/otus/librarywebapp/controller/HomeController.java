package ru.otus.librarywebapp.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

import java.util.HashMap;

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
        HashMap<Object, Object> data = new HashMap<>();
        data.put("authors", authorService.getAll());
        data.put("genres", genreService.getAll());
        data.put("books", bookService.getAll());
        data.put("comments", commentService.getAll());
        model.addAttribute("frontendData", data);
        return "index";
    }

}
