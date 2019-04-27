package ru.otus.librarywebapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@Document(collection = "authors")
public class Author {

    @Id
    private String id;
    @NotBlank
    private String firstName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Europe/Moscow")
    @NotNull
    private Date birthDate;
    @Indexed
    @NotBlank
    private String lastName;

    public Author(String firstName, Date birthDate, String lastName) {
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", Objects.toString(firstName, ""),
                Objects.toString(lastName, ""));
    }

}
