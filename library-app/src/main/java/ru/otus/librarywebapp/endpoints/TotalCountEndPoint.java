package ru.otus.librarywebapp.endpoints;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RestControllerEndpoint(id="counts")
public class TotalCountEndPoint {

    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Autowired
    public TotalCountEndPoint(AuthorService authorService, BookService bookService,
                              GenreService genreService, CommentService commentService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @GetMapping("/authors")
    @ResponseBody
    public ResponseEntity<Map> authors() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("authors", authorService.count()));
    }

    @GetMapping("/books")
    @ResponseBody
    public ResponseEntity<Map> books() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("books", bookService.count()));
    }

    @GetMapping("/genres")
    @ResponseBody
    public ResponseEntity<Map> genres() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("genres", genreService.count()));
    }

    @GetMapping("/comments")
    @ResponseBody
    public ResponseEntity<Map> comments() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap("comments", commentService.count()));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Map> totalCount(HttpRequest httpRequest) {
        Map<String, Link> links = new HashMap<>();
        links.put("self", new Link(newPath(httpRequest.getURI(), "")));
        links.put("authors", new Link(newPath(httpRequest.getURI(), "/authors")));
        links.put("books", new Link(newPath(httpRequest.getURI(), "/books")));
        links.put("genres", new Link(newPath(httpRequest.getURI(), "/genres")));
        links.put("comments", new Link(newPath(httpRequest.getURI(), "/comments")));
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("_links", links));
    }

    private String newPath(URI uri, String path) {
        return UriComponentsBuilder.fromUri(uri).path(path).toUriString();
    }

}
