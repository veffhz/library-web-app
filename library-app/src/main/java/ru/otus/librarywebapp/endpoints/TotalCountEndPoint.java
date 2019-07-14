package ru.otus.librarywebapp.endpoints;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<Map>> authors() {
        return authorService.count()
                .map(count -> ResponseEntity.status(HttpStatus.OK)
                        .body(Collections.singletonMap("authors", count)));
    }

    @GetMapping("/books")
    @ResponseBody
    public Mono<ResponseEntity<Map>> books() {
        return bookService.count()
                .map(count -> ResponseEntity.status(HttpStatus.OK)
                        .body(Collections.singletonMap("books", count)));
    }

    @GetMapping("/genres")
    @ResponseBody
    public Mono<ResponseEntity<Map>> genres() {
        return genreService.count()
                .map(count -> ResponseEntity.status(HttpStatus.OK)
                        .body(Collections.singletonMap("genres", count)));
    }

    @GetMapping("/comments")
    @ResponseBody
    public Mono<ResponseEntity<Map>> comments() {
        return commentService.count()
                .map(count -> ResponseEntity.status(HttpStatus.OK)
                        .body(Collections.singletonMap("comments", count)));
    }


    @GetMapping
    @ResponseBody
    public Mono<ResponseEntity<Map>> totalCount(ServerHttpRequest serverHttpRequest) {
        Map<String, String> links = new HashMap<>();
        links.put("authors", newPath(serverHttpRequest.getURI(), "/authors"));
        links.put("books", newPath(serverHttpRequest.getURI(), "/books"));
        links.put("genres", newPath(serverHttpRequest.getURI(), "/genres"));
        links.put("comments", newPath(serverHttpRequest.getURI(), "/comments"));
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(links));
    }

    private String newPath(URI uri, String path) {
        return UriComponentsBuilder.fromUri(uri).path(path).toUriString();
    }

}
