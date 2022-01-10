package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.service.IBookService;
import br.com.franca.libraryapi.dtos.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final IBookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO save(@RequestBody @Valid BookDTO dto) {

        return bookService.save(dto);

    }
}
