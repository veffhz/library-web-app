package ru.otus.validateapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.otus.domain.AdditionalData;
import ru.otus.dto.BookDto;
import ru.otus.validateapp.service.BookValidateService;

@Slf4j
@RestController
public class Api {

    private final BookValidateService bookValidateService;

    @Autowired
    public Api(BookValidateService bookValidateService) {
        this.bookValidateService = bookValidateService;
    }

    @PostMapping("/api/data")
    @ResponseStatus(HttpStatus.OK)
    public void data(@RequestBody BookDto dto) {
        log.info("received books {}", dto.getBooks());
        bookValidateService.validate(dto.getBooks());
    }

    @GetMapping("/api/data/{id}")
    public AdditionalData getById(@PathVariable String id) {
        log.info("get data by id {}", id);
        return bookValidateService.findById(id);
    }

}
