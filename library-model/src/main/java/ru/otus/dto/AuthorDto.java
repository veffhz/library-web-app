package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import ru.otus.domain.Author;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthorDto {
    private List<Author> authors;
}
