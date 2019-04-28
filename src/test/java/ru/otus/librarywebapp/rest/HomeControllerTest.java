package ru.otus.librarywebapp.rest;

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

import ru.otus.librarywebapp.controller.HomeController;
import ru.otus.librarywebapp.domain.*;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test for Home Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HomeController.class)
@Import(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Test get info page on / ")
    void shouldGetInfoPage() throws Exception {

        HashMap<Object, Object> data = new HashMap<>();

        List<Author> authors = Collections.singletonList(new Author());
        data.put("authors", authors);
        List<Genre> genres = Collections.singletonList(new Genre());
        data.put("genres", genres);
        List<Book> books = Collections.singletonList(new Book());
        data.put("books", books);
        List<Comment> comments = Collections.singletonList(new Comment());
        data.put("comments", comments);

        given(this.authorService.getAll()).willReturn(authors);
        given(this.genreService.getAll()).willReturn(genres);
        given(this.bookService.getAll()).willReturn(books);
        given(this.commentService.getAll()).willReturn(comments);

        this.mvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("frontendData", data));
    }
}