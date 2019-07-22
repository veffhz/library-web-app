package ru.otus.librarywebapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

import ru.otus.librarywebapp.exception.CommentNotFoundException;
import ru.otus.librarywebapp.service.CommentService;

import javax.validation.Valid;

@Slf4j
@RestController
public class CommentApi {

    private static final String COMMENTS_SORT_FIELD = "date";

    private static final Sort COMMENT_SORT = Sort.by(Sort.Direction.ASC, COMMENTS_SORT_FIELD);

    public static final int COMMENTS_PER_PAGE = 5;

    public static final PageRequest COMMENTS_PAGE_REQUEST = PageRequest.of(0, COMMENTS_PER_PAGE, COMMENT_SORT);

    private final CommentService commentService;

    @Autowired
    public CommentApi(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/comment")
    public CommentDto getAll(@RequestParam("page") int page) {
        log.info("get all comments, page {}", page);
        return commentService.getAll(PageRequest.of(page, COMMENTS_PER_PAGE, COMMENT_SORT));
    }

    @GetMapping("/api/comment/{id}")
    public Comment getById(@PathVariable String id) {
        log.info("get comment by id {}", id);
        return commentService.getById(id)
                .orElseThrow(CommentNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/comment")
    public Comment update(@Valid @RequestBody Comment comment) {
        log.info("update comment {} by id {}", comment, comment.getId());
        return commentService.update(comment);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/comment")
    public Comment create(@Valid @RequestBody Comment comment) {
        log.info("create comment {}", comment);
        return commentService.insert(comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/comment/{id}")
    public void delete(@PathVariable String id) {
        log.info("delete comment by id {}", id);
        commentService.deleteById(id);
    }

}
