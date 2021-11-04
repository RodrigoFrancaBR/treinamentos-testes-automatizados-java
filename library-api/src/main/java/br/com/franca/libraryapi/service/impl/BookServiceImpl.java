package br.com.franca.libraryapi.service.impl;

import br.com.franca.libraryapi.model.entity.Book;
import br.com.franca.libraryapi.repository.BookRepository;
import br.com.franca.libraryapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl  implements BookService {

    private final BookRepository repository;

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}
