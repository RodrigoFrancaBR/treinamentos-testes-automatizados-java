package br.com.franca.libraryapi.api.service;


import br.com.franca.libraryapi.domain.model.Book;
import org.springframework.stereotype.Service;

@Service
public interface IBookService {
    Book save(Book book);
}
