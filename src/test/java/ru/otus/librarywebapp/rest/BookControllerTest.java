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
import ru.otus.librarywebapp.domain.Author;
import ru.otus.librarywebapp.domain.Book;
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

@DisplayName("Test for book Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookApi.class)
class BookControllerTest {

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
    @DisplayName("Test get all books page on /books")
    void shouldGetAllBooksPage() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(),
                new Book(),
                new Book());

        given(this.bookService.getAll()).willReturn(books);

        this.mvc.perform(get("/books").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("book/books"))
                .andExpect(model().attribute("books", books))
                .andExpect(content().string(containsString("Book name")))
                .andExpect(content().string(containsString("Books:")));
    }

    @Test
    @DisplayName("Test get book page on /book by id")
    void shouldGetBookPage() throws Exception {
        Book book = new Book();
        given(this.bookService.getById("123")).willReturn(Optional.of(book));

        this.mvc.perform(get("/book")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("book/book"))
                .andExpect(model().attribute("book", book))
                .andExpect(content().string(containsString("Book name:")))
                .andExpect(content().string(containsString("Book info:")));
    }

    @Test
    @DisplayName("Test edit book page on /book/edit")
    void shouldEditBookPage() throws Exception {
        Book book = new Book();
        given(this.bookService.getById("123")).willReturn(Optional.of(book));

        this.mvc.perform(get("/book/edit")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("book", book))
                .andExpect(content().string(containsString("Book name:")))
                .andExpect(content().string(containsString("Book edit:")));
    }

    @Test
    @DisplayName("Test save book on post /book/edit")
    void shouldSaveBook() throws Exception {
        this.mvc.perform(post("/book/edit")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book?id=123"));
        verify(this.bookService, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Test delete book on post /book/delete")
    void shouldDeleteBookById() throws Exception {
        this.mvc.perform(post("/book/delete")
                .param("id", "123")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
        verify(this.bookService, times(1)).deleteById("123");
    }

    @Test
    @DisplayName("Test add book page on /book/add")
    void shouldAddBookPage() throws Exception {
        Book book = new Book();
        book.setAuthor(new Author());
        book.setGenre(new Genre());

        this.mvc.perform(get("/book/add")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("book", book))
                .andExpect(content().string(containsString("Book name:")))
                .andExpect(content().string(containsString("Book edit:")));
    }
}