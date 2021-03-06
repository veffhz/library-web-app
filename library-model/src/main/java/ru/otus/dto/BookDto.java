package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import ru.otus.domain.Book;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookDto {
    private List<Book> books;
    private int currentPage;
    private Long totalPages;
}
