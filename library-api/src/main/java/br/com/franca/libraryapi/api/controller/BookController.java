package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.service.BookService;
import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody BookDTO dto) {

        Book book = Book.builder().title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .build();
        Book bookSave = bookService.save(book);
        bookSave.setId(1l);

        BookDTO dtoSaved = BookDTO.builder().id(bookSave.getId())
                .title(bookSave.getTitle())
                .author(bookSave.getAuthor())
                .isbn(bookSave.getIsbn())
                .build();

        return dtoSaved;
    }
}
