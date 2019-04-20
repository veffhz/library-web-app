package ru.otus.librarywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

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
    public String index() {
        return "redirect:/info";
    }

    @GetMapping("/info")
    public String genrePage(Model model) {
        long authors = authorService.count();
        model.addAttribute("authors", authors);
        long genres = genreService.count();
        model.addAttribute("genres", genres);
        long books = bookService.count();
        model.addAttribute("books", books);
        long comments = commentService.count();
        model.addAttribute("comments", comments);
        return "info";
    }

}
