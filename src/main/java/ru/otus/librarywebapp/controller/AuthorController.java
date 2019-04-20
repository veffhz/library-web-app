package ru.otus.librarywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.view.RedirectView;
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.exception.AuthorNotFoundException;
import ru.otus.librarywebapp.service.AuthorService;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String authorsPage(Model model) {
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "author/authors";
    }

    @GetMapping("/author")
    public String authorPage(@RequestParam("id") String id, Model model) {
        Author author = authorService.getById(id).orElseThrow(AuthorNotFoundException::new);
        model.addAttribute("author", author);
        return "author/author";
    }

    @GetMapping("/editAuthor")
    public String edit(@RequestParam("id") String id, Model model) {
        Author author = authorService.getById(id).orElseThrow(AuthorNotFoundException::new);
        model.addAttribute("author", author);
        return "author/edit";
    }

    @PostMapping("/editAuthor")
    public RedirectView save(@ModelAttribute("author") Author author) {
        authorService.save(author);
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/author?id={id}");
        rv.getAttributesMap().put("id", author.getId());
        return rv;
    }

}
