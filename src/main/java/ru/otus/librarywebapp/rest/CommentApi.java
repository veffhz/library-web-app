package ru.otus.librarywebapp.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.exception.CommentNotFoundException;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("/api/comment")
    public List<Comment> getAll() {
        log.info("get all comments");
        return commentService.getAll();
    }

    @GetMapping("/api/comment/{id}")
    public Comment getById(@PathVariable String id) {
        log.info("get comment by id {}", id);
        return commentService.getById(id).orElseThrow(CommentNotFoundException::new);
    }

    @PutMapping("/api/comment")
    public ResponseEntity<Comment> update(@Valid @RequestBody Comment comment) {
        log.info("update comment {} by id {}", comment, comment.getId());
        Comment updatedComment = commentService.update(comment);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @PostMapping("/api/comment")
    public ResponseEntity<Comment> create(@Valid @RequestBody Comment comment) {
        log.info("create comment {}", comment);
        Comment savedGenre = commentService.insert(comment);
        return new ResponseEntity<>(savedGenre, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        log.info("delete comment by id {}", id);
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
