package ru.otus.librarywebapp.domain;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "genres")
@EqualsAndHashCode
public class Genre {

    @Id
    private String id;
    @Indexed
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

}
