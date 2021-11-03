package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.dto.BookDTO;
import br.com.franca.libraryapi.model.entity.Book;
import br.com.franca.libraryapi.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO save(@RequestBody BookDTO bookDTO){

      log.info("requesting POST: /api/books :: {}", bookDTO);
      Book book = modelMapper.map(bookDTO, Book.class);
//
//      log.info("saving book :: {}", book);
//      Book saveBook = service.save(book);

      BookDTO saveBookDTO = modelMapper.map(book, BookDTO.class);

      return saveBookDTO;

    }
}
