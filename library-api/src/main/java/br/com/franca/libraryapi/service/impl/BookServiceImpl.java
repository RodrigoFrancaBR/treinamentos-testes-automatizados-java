package br.com.franca.libraryapi.service.impl;

import br.com.franca.libraryapi.model.entity.Book;
import br.com.franca.libraryapi.repository.BookRepository;
import br.com.franca.libraryapi.service.BookService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl {// implements BookService {

    private BookRepository repository;

    // @Override
    public Book save(Book book) {
//        book.setId(2l);
//        return book;
        return repository.save(book);
    }
}
