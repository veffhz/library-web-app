package ru.otus.librarywebapp.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.exception.AuthorNotFoundException;
import ru.otus.librarywebapp.service.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String authors(Model model) {
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "author/authors";
    }

    @GetMapping("/author")
    public String author(@RequestParam("id") String id, Model model) {
        Author author = authorService.getById(id).orElseThrow(AuthorNotFoundException::new);
        model.addAttribute("author", author);
        return "author/author";
    }

}
