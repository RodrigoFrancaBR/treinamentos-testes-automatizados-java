package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.service.IBookService;
import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dtos.BookDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO save(@RequestBody @Valid BookDTO dto) {

        Book book = mapper.map(dto, Book.class);

        Book bookSave = bookService.save(book);

        BookDTO dtoSaved = mapper.map(bookSave, BookDTO.class);

        return dtoSaved;
    }
}
