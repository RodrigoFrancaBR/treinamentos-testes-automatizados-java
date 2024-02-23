package br.com.franca.libraryapi.api.service;

import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dtos.BookDTO;
import br.com.franca.libraryapi.exception.BusinessException;
import br.com.franca.libraryapi.exception.ValidationException;
import br.com.franca.libraryapi.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final Validator validator;

    private final ModelMapper mapper;

    private final IBookRepository repository;

    @Override
    public Long save(BookDTO dto) {
        dto.setTitle("");
        dto.setAuthor("");

        validator.validate(dto).stream()
                .findFirst()
                .ifPresent((e) -> {
                    throw new ValidationException("O " + e.getPropertyPath() + e.getMessage());
                });

        repository.existsByIsbn(dto.getIsbn())
                .ifPresent((e) -> {
                    throw new BusinessException("The field isbn already registered");
                });

        Book book = mapper.map(dto, Book.class);

        return repository.save(book).getId();
    }

}
