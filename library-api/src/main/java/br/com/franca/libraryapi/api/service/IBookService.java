package br.com.franca.libraryapi.api.service;


import br.com.franca.libraryapi.dtos.BookDTO;

public interface IBookService {
    BookDTO save(BookDTO dto);
}
