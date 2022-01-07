package br.com.franca.libraryapi.api.service;

import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.domain.repository.IBookRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookService implements IBookService {

    private final IBookRepository repository;

    @Override
    public Book save(Book book) {
        Book savedBook = repository.save(book);
        return savedBook;
    }
}
