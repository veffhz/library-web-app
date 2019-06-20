package ru.otus.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LibraryDto {

    private String bookName;
    private String author;
    private String year;
    private String genre;
    private String isbn;

}
