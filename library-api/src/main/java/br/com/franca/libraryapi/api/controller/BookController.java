package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.dto.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO save(BookDTO bookDTO){
      log.info("requesting POST: /api/books :: {}", bookDTO);
        return BookDTO.builder()
                .id(1l)
                .title("My Book")
                .author("Author")
                .isbn("123456")
                .build();
    }
}
