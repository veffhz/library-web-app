package ru.otus.librarywebapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

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
