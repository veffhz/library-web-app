package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import ru.otus.librarywebapp.dao.AuthorRepository;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;
import ru.otus.librarywebapp.dao.GenreRepository;
import ru.otus.librarywebapp.domain.Genre;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test for genre Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    //Все бины нужны, потому что иначе spring начинает искать mongoTemplate bean, и не находит
    @MockBean
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreService genreService;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Test get genres authors page on /genres")
    void shouldGetAllGenresPage() throws Exception {
        List<Genre> genres = Arrays.asList(
                new Genre("test"),
                new Genre("test"),
                new Genre("test"));

        given(this.genreService.getAll()).willReturn(genres);

        this.mvc.perform(get("/genres").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("genre/genres"))
                .andExpect(model().attribute("genres", genres))
                .andExpect(content().string(containsString("Genre Name")))
                .andExpect(content().string(containsString("Genres:")));
    }

    @Test
    @DisplayName("Test get genre page on /genre by id")
    void shouldGetGenrePage() throws Exception {
        Genre genre = new Genre("test");
        given(this.genreService.getById("123")).willReturn(Optional.of(genre));

        this.mvc.perform(get("/genre")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("genre/genre"))
                .andExpect(model().attribute("genre", genre))
                .andExpect(content().string(containsString("Genre name:")))
                .andExpect(content().string(containsString("Genre info:")));
    }

    @Test
    @DisplayName("Test edit genre page on /author/genre")
    void shouldEditGenrePage() throws Exception {
        Genre genre = new Genre("test");
        given(this.genreService.getById("123")).willReturn(Optional.of(genre));

        this.mvc.perform(get("/genre/edit")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("genre/edit"))
                .andExpect(model().attribute("genre", genre))
                .andExpect(content().string(containsString("Genre name:")))
                .andExpect(content().string(containsString("Genre edit:")));
    }

    @Test
    @DisplayName("Test save genre on post /genre/edit")
    void shouldSaveGenre() throws Exception {
        this.mvc.perform(post("/genre/edit")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genres"));
        verify(this.genreService, times(1)).save(any(Genre.class));
    }

    @Test
    @DisplayName("Test delete genre on post /genre/delete")
    void shouldDeleteGenreById() throws Exception {
        this.mvc.perform(post("/genre/delete")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genres"));
        verify(this.genreService, times(1)).deleteById("123");
    }

    @Test
    @DisplayName("Test add genre page on /genre/add")
    void shouldAddGenrePage() throws Exception {
        this.mvc.perform(get("/genre/add")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("genre/edit"))
                .andExpect(model().attribute("genre", new Genre()))
                .andExpect(content().string(containsString("Genre name:")))
                .andExpect(content().string(containsString("Genre edit:")));
    }
}