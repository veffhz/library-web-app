package ru.otus.librarywebapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test for genre Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GenreApi.class)
@Import(GenreApi.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("Test get genres on /api/genre")
    void shouldGetAllGenres() throws Exception {
        List<Genre> genres = Arrays.asList(
                new Genre("test"),
                new Genre("test"));

        given(this.genreService.getAll()).willReturn(genres);

        String responseBody = "[{\"id\":null,\"genreName\":\"test\"},{\"id\":null,\"genreName\":\"test\"}]";

        this.mvc.perform(get("/api/genre")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test get genre on /api/genre/{id}")
    void shouldGetGenre() throws Exception {
        Genre genre = new Genre("test");

        given(this.genreService.getById("123")).willReturn(Optional.of(genre));

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        this.mvc.perform(get("/api/genre/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test update genre on /api/genre")
    void shouldUpdateGenre() throws Exception {
        Genre genre = new Genre("test");

        given(this.genreService.update(genre)).willReturn(genre);

        ObjectMapper mapper = new ObjectMapper();

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        this.mvc.perform(put("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(genre))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(this.genreService, times(1)).update(any(Genre.class));
    }

    @Test
    @DisplayName("Test create genre on post /api/genre")
    void shouldCreateGenre() throws Exception {
        Genre genre = new Genre("test");

        given(this.genreService.insert(genre)).willReturn(genre);

        String responseBody = "{\"id\":null,\"genreName\":\"test\"}";

        ObjectMapper mapper = new ObjectMapper();

        this.mvc.perform(post("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(genre))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));
        verify(this.genreService, times(1)).insert(any(Genre.class));
    }

    @Test
    @DisplayName("Test delete genre on /api/genre/{id}")
    void shouldDeleteGenreById() throws Exception {
        this.mvc.perform(delete("/api/genre/123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isNoContent());
        verify(this.genreService, times(1)).deleteById("123");
    }

}