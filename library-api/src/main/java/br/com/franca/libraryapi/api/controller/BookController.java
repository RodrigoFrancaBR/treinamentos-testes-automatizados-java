package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook() {
        return BookDTO.builder()
                .id(1l)
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();
    }
}
