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
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.exception.BookNotFoundException;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.GenreService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/books")
    public String booksPage(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "book/books";
    }

    @GetMapping("/book")
    public String bookPage(@RequestParam("id") String id, Model model) {
        Book book = bookService.getById(id).orElseThrow(BookNotFoundException::new);
        model.addAttribute("book", book);
        return "book/book";
    }

    @GetMapping("/editBook")
    public String edit(@RequestParam("id") String id, Model model) {
        Book book = bookService.getById(id).orElseThrow(BookNotFoundException::new);
        model.addAttribute("book", book);
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "book/edit";
    }

    @PostMapping("/editBook")
    public RedirectView save(@ModelAttribute("book") Book book) {
        bookService.save(book);
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/book?id={id}");
        rv.getAttributesMap().put("id", book.getId());
        return rv;
    }

}
