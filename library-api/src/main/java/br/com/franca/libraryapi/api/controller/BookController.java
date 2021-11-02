package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.dto.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO save(@RequestBody BookDTO bookDTO){
      log.info("requesting POST: /api/books :: {}", bookDTO);
        return bookDTO;
    }
}
