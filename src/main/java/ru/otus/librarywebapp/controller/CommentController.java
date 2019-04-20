package ru.otus.librarywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.view.RedirectView;
import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.exception.CommentNotFoundException;
import ru.otus.librarywebapp.service.CommentService;

import java.util.List;

@Controller
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public String commentsPage(Model model) {
        List<Comment> comments = commentService.getAll();
        model.addAttribute("comments", comments);
        return "comment/comments";
    }

    @GetMapping("/comment")
    public String commentPage(@RequestParam("id") String id, Model model) {
        Comment comment = commentService.getById(id).orElseThrow(CommentNotFoundException::new);
        model.addAttribute("comment", comment);
        return "comment/comment";
    }

    @GetMapping("/comment/delete")
    public RedirectView delete(@RequestParam("id") String id) {
        commentService.deleteById(id);
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/comments");
        return rv;
    }

}
