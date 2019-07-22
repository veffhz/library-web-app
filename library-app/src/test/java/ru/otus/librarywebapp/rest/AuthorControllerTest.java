package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import ru.otus.domain.Author;

import ru.otus.librarywebapp.service.AuthorService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test for author api")
@WebMvcTest(controllers = AuthorApi.class)
@Import(AuthorApi.class)
class AuthorControllerTest extends BaseTest {

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("Test get all authors page on /api/author")
    void shouldGetAllAuthors() throws Exception {
        given(this.authorService.getAll()).willReturn(Arrays.asList(author(), author()));

        String responseBody = "[{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}," +
                "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}]";

        this.webClient.perform(get("/api/author")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get author on /api/author/{id}")
    void shouldGetAuthor() throws Exception {
        given(this.authorService.getById("123")).willReturn(Optional.of(author()));

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        this.webClient.perform(get("/api/author/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update author on /api/author")
    void shouldUpdateAuthor() throws Exception {
        Author author = author();

        given(this.authorService.update(author)).willReturn(author);

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        this.webClient.perform(put("/api/author").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.authorService, times(1)).update(any(Author.class));
    }

    @Test
    @DisplayName("Test create author on post /api/author")
    void shouldCreateAuthor() throws Exception {
        Author author = author();

        given(this.authorService.insert(author)).willReturn(author);

        String responseBody = "{\"id\":null,\"firstName\":\"test\",\"birthDate\":\"2019-04-27\",\"lastName\":\"test\",\"fullName\":\"test test\"}";

        this.webClient.perform(post("/api/author").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));

        verify(this.authorService, times(1)).insert(any(Author.class));
    }

    @Test
    @DisplayName("Test delete author on /api/author/{id}")
    @WithMockUser(username = "adm", authorities = "ROLE_ADMIN") // TODO check admin
    void shouldDeleteAuthorById() throws Exception {
        this.webClient.perform(delete("/api/author/123").with(csrf())
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isNoContent());
        verify(this.authorService, times(1)).deleteById("123");
    }

    private Author author() {
        return new Author("test", date(), "test");
    }

}