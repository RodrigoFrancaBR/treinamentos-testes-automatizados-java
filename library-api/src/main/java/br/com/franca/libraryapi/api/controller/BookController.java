package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.service.BookService;
import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dto.BookDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    ModelMapper mapper;

    public BookController(BookService bookService, ModelMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody BookDTO dto) {

        Book book = mapper.map(dto, Book.class);

        Book bookSave = bookService.save(book);
        bookSave = book;
        bookSave.setId(1l);

        BookDTO dtoSaved = mapper.map(bookSave, BookDTO.class);

        return dtoSaved;
    }
}
