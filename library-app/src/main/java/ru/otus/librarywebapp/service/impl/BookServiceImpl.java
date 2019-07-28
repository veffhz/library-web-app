package ru.otus.librarywebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.otus.dto.BookDto;
import ru.otus.librarywebapp.dao.BookRepository;
import ru.otus.librarywebapp.dao.CommentRepository;

import ru.otus.domain.Book;
import ru.otus.librarywebapp.rest.BookApi;
import ru.otus.librarywebapp.service.BookService;
import ru.otus.librarywebapp.utils.Helper;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookServiceImpl(BookRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Mono<Long> count() {
        return repository.count();
    }

    public Mono<Book> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Book> getByBookName(String bookName) {
        return repository.findByBookName(bookName);
    }

    @Override
    public Flux<Book> getByBookPartName(String bookName) {
        return repository.findByBookNameContaining(bookName);
    }

    @Override
    public Flux<Book> getAll() {
        return HystrixCommands
                .from(repository.findAll())
                .fallback(fallback())
                .commandName("findAll")
                .toFlux();
    }

    @Override
    public Mono<BookDto> getAll(Pageable pageable) {
        return HystrixCommands.from(repository.findAll(pageable).collectList().zipWith(repository.count())
                .map(data -> new BookDto(data.getT1(), pageable.getPageNumber(),
                        data.getT2() / BookApi.BOOKS_PER_PAGE)))
                .fallback(fallbackMono())
                .commandName("findAll")
                .toMono();
    }

    private Flux<Book> fallback() {
        return Flux.fromIterable(Helper.notAvailableBooks());
    }

    private Mono<BookDto> fallbackMono() {
        return Mono.just(Helper.notAvailableBookDto());
    }

    @Override
    public Flux<Book> deleteByAuthorId(String authorId) {
        return repository.findByAuthorId(authorId)
                .flatMap(book -> commentRepository.deleteByBookId(book.getId()))
                .thenMany(repository.deleteByAuthorId(authorId));
    }

    @Override
    public Flux<Book> deleteByGenreId(String genreId) {
        return repository.findByGenreId(genreId)
                .flatMap(book -> commentRepository.deleteByBookId(book.getId()))
                .thenMany(repository.deleteByGenreId(genreId));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return commentRepository.deleteByBookId(id).then(repository.deleteById(id));
    }

    @Override
    public Mono<Book> insert(Book book) {
        return repository.insert(book);
    }

    @Override
    public Mono<Book> update(Book book) {
        return repository.save(book);
    }

}
