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
import ru.otus.librarywebapp.domain.Book;
import ru.otus.librarywebapp.domain.Comment;
import ru.otus.librarywebapp.service.AuthorService;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.service.CommentService;
import ru.otus.librarywebapp.service.GenreService;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test for comment Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

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
    @DisplayName("Test get all comments page on /comments")
    void shouldGetAllCommentsPage() throws Exception {
        Book book = new Book();
        Comment comment = new Comment("test", new Date(), "test");
        comment.setBook(book);
        List<Comment> comments = Arrays.asList(comment, comment, comment);

        given(this.commentService.getAll()).willReturn(comments);

        this.mvc.perform(get("/comments").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/comments"))
                .andExpect(model().attribute("comments", comments))
                .andExpect(content().string(containsString("Book name")))
                .andExpect(content().string(containsString("List of all comments")));
    }

    @Test
    @DisplayName("Test get comment page on /comment by id")
    void shouldGetCommentPage() throws Exception {
        Comment comment = new Comment("test", new Date(), "test");
        given(this.commentService.getById("123")).willReturn(Optional.of(comment));

        this.mvc.perform(get("/comment")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/comment"))
                .andExpect(model().attribute("comment", comment))
                .andExpect(content().string(containsString("Content:")))
                .andExpect(content().string(containsString("Comment info:")));
    }

    @Test
    @DisplayName("Test delete comment on post /comment/delete")
    void shouldDeleteCommentById() throws Exception {
        this.mvc.perform(post("/comment/delete")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments"));
        verify(this.commentService, times(1)).deleteById("123");
    }

    @Test
    @DisplayName("Test add comment page on /comment/add")
    void shouldAddCommentPage() throws Exception {
        List<Book> books = Collections.singletonList(new Book());
        given(this.bookService.getAll()).willReturn(books);
        Comment comment = new Comment();
        comment.setBook(books.get(0));
        given(this.commentService.getById("123")).willReturn(Optional.of(comment));

        this.mvc.perform(get("/comment/add")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/new"))
                .andExpect(model().attribute("comment", comment))
                .andExpect(model().attribute("books", books))
                .andExpect(content().string(containsString("Author:")))
                .andExpect(content().string(containsString("Content:")));
    }

    @Test
    @DisplayName("Test save comment on post /comment/add")
    void shouldSaveComment() throws Exception {
        this.mvc.perform(post("/comment/add")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments"));
        verify(this.commentService, times(1)).save(any(Comment.class));
    }

}