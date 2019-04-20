package ru.otus.librarywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.exception.GenreNotFoundException;
import ru.otus.librarywebapp.service.GenreService;

import java.util.List;

@Controller
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String genresPage(Model model) {
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "genre/genres";
    }

    @GetMapping("/genre")
    public String genrePage(@RequestParam("id") String id, Model model) {
        Genre genre = genreService.getById(id).orElseThrow(GenreNotFoundException::new);
        model.addAttribute("genre", genre);
        return "genre/genre";
    }

    @GetMapping("/editGenre")
    public String edit(@RequestParam("id") String id, Model model) {
        Genre genre = genreService.getById(id).orElseThrow(GenreNotFoundException::new);
        model.addAttribute("genre", genre);
        return "genre/edit";
    }

    @PostMapping("/editGenre")
    public RedirectView save(@ModelAttribute("genre") Genre genre) {
        genreService.save(genre);
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/genre?id={id}");
        rv.getAttributesMap().put("id", genre.getId());
        return rv;
    }

}
