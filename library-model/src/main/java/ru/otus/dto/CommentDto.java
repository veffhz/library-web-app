package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import ru.otus.domain.Comment;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class CommentDto {
    private List<Comment> comments;
    private int currentPage;
    private Long totalPages;
}
