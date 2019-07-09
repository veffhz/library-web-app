package ru.otus.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDto {

    private String bookName;
    private String author;
    private String year;
    private String genre;
    private String isbn;

}
