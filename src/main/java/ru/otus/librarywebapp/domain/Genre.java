package ru.otus.librarywebapp.domain;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Document(collection = "genres")
public class Genre {

    @Id
    private String id;
    @Indexed
    @NotBlank
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

}
