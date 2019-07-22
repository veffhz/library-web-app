package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.dto.*;

import ru.otus.librarywebapp.controller.HomeController;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test for Home Controller")
@WebMvcTest(controllers = HomeController.class)
@Import(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc webClient;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Test redirect on / ")
    void shouldRedirectToLogin() throws Exception {
        this.webClient.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test get info page on / ")
    @WithMockUser
    void shouldGetInfoPage() throws Exception {
        given(this.authorService.getAll()).willReturn(Collections.singletonList(new Author()));
        given(this.genreService.getAll()).willReturn(Collections.singletonList(new Genre()));
        given(this.bookService.getAll(BookApi.BOOK_PAGE_REQUEST))
                .willReturn(new BookDto(Collections.emptyList(), 0, 0L));
        given(this.commentService.getAll(CommentApi.COMMENTS_PAGE_REQUEST))
                .willReturn(new CommentDto(Collections.emptyList(), 0, 0L));

        FrontendDto frontendData = new FrontendDto()
                .withAuthors(new AuthorDto(authorService.getAll()))
                .withGenres(new GenreDto(genreService.getAll()))
                .withBooks(bookService.getAll(BookApi.BOOK_PAGE_REQUEST))
                .withComments(commentService.getAll(CommentApi.COMMENTS_PAGE_REQUEST));

        this.webClient.perform(get("/")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("frontendData", frontendData));
    }

}