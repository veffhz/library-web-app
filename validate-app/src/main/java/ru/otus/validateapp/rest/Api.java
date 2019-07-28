package ru.otus.validateapp.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.otus.domain.AdditionalData;
import ru.otus.dto.BookDto;
import ru.otus.validateapp.service.BookValidateService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class Api {

    private final BookValidateService bookValidateService;

    @Autowired
    public Api(BookValidateService bookValidateService) {
        this.bookValidateService = bookValidateService;
    }

    @PostMapping("/api/data")
    public ResponseEntity validate(@RequestBody BookDto dto) {
        log.info("received books {}", dto.getBooks());
        bookValidateService.validate(dto.getBooks());
        if (false) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/data/{id}")
    public ResponseEntity<AdditionalData> getById(@PathVariable String id) {
        log.info("get data by id {}", id);
        Optional<AdditionalData> additionalData = bookValidateService.findById(id);
        return additionalData.map(data -> ResponseEntity.status(HttpStatus.OK).body(data))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/api/data")
    public List<AdditionalData> getAll() {
        log.info("get all data");
        return bookValidateService.findAll();
    }

    @DeleteMapping("/api/data")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        log.info("delete all data");
        bookValidateService.drop();
    }

}
