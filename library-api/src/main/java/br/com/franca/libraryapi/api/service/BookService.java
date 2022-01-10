package br.com.franca.libraryapi.api.service;

import br.com.franca.libraryapi.domain.MessageError;
import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dtos.BookDTO;
import br.com.franca.libraryapi.exception.BusinessException;
import br.com.franca.libraryapi.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final ModelMapper mapper;

    private final IBookRepository repository;

    @Override
    public BookDTO save(BookDTO dto) {

        Book book = mapper.map(dto, Book.class);

        if (repository.existsByIsbn(book.getIsbn())) {
            throw new BusinessException(new MessageError("isbn", "The field isbn already registered"));
        }

        Book savedBook = repository.save(book);

        return mapper.map(savedBook, BookDTO.class);

    }
}
