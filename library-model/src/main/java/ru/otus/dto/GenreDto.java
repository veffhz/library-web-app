package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import ru.otus.domain.Genre;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GenreDto {
    private List<Genre> genres;
}
