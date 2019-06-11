package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.domain.Comment;

import ru.otus.librarywebapp.exception.CommentNotFoundException;
import ru.otus.librarywebapp.service.CommentService;

import javax.validation.Valid;

@Slf4j
@RestController
public class CommentApi {

    private final CommentService commentService;

    @Autowired
    public CommentApi(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/comment")
    public Flux<Comment> getAll() {
        log.info("get all comments");
        return commentService.getAll();
    }

    @GetMapping("/api/comment/{id}")
    public Mono<Comment> getById(@PathVariable String id) {
        log.info("get comment by id {}", id);
        return commentService.getById(id)
                .switchIfEmpty(Mono.error(CommentNotFoundException::new));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/comment")
    public Mono<Comment> update(@Valid @RequestBody Comment comment) {
        log.info("update comment {} by id {}", comment, comment.getId());
        return commentService.update(comment);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/comment")
    public Mono<Comment> create(@Valid @RequestBody Comment comment) {
        log.info("create comment {}", comment);
        return commentService.insert(comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/comment/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        log.info("delete comment by id {}", id);
        return commentService.deleteById(id);
    }

}
