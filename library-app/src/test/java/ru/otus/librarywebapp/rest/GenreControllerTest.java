package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import ru.otus.domain.Genre;

import ru.otus.librarywebapp.service.GenreService;

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

@DisplayName("Test for genre api")
@WebMvcTest(controllers = GenreApi.class)
@Import(GenreApi.class)
class GenreControllerTest extends BaseTest {

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("Test get genres on /api/genre")
    void shouldGetAllGenres() throws Exception {
        given(this.genreService.getAll()).willReturn(Arrays.asList(genre(), genre()));

        String responseBody = "[{\"id\":null,\"genreName\":\"test\"},{\"id\":null,\"genreName\":\"test\"}]";

        this.webClient.perform(get("/api/genre")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get genre on /api/genre/{id}")
    void shouldGetGenre() throws Exception {
        given(this.genreService.getById("123")).willReturn(Optional.of(genre()));

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        this.webClient.perform(get("/api/genre/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update genre on /api/genre")
    void shouldUpdateGenre() throws Exception {
        Genre genre = genre();

        given(this.genreService.update(genre)).willReturn(genre());

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        this.webClient.perform(put("/api/genre").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(genre)))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.genreService, times(1)).update(any(Genre.class));
    }

    @Test
    @DisplayName("Test create genre on post /api/genre")
    void shouldCreateGenre() throws Exception {
        Genre genre = genre();

        given(this.genreService.insert(genre)).willReturn(genre());

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        this.webClient.perform(post("/api/genre").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(genre)))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));

        verify(this.genreService, times(1)).insert(any(Genre.class));
    }

    @Test
    @DisplayName("Test delete genre on /api/genre/{id}")
    void shouldDeleteGenreById() throws Exception {
        this.webClient.perform(delete("/api/genre/123").with(csrf()))
                .andExpect(status().isNoContent());

        verify(this.genreService, times(1)).deleteById("123");
    }

    private Genre genre() {
        return new Genre("test");
    }

}