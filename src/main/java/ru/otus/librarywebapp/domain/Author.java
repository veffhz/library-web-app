package ru.otus.librarywebapp.domain;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "authors")
@EqualsAndHashCode
public class Author {

    @Id
    private String id;
    private String firstName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    @Indexed
    private String lastName;

    public Author(String firstName, Date birthDate, String lastName) {
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}
