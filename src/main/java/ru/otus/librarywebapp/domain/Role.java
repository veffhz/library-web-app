package ru.otus.librarywebapp.domain;

import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Document(collection = "roles")
public class Role implements GrantedAuthority {

    @Id
    private String id;
    @NotBlank
    private String roleName;
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;

    public Role() {
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public String getAuthority() {
        return id;
    }
}
