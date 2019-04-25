package ru.otus.librarywebapp.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @GetMapping("/info")
    public String info(Model model) {
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
