package br.com.franca.libraryapi.api.service;

import br.com.franca.libraryapi.api.helper.MockHelper;
import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dtos.BookDTO;
import br.com.franca.libraryapi.exception.BusinessException;
import br.com.franca.libraryapi.exception.ValidationException;
import br.com.franca.libraryapi.repository.IBookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @MockBean
    Validator validator;

    @MockBean
    ModelMapper mapper;

    @MockBean
    IBookRepository repository;

    IBookService bookService;

    @BeforeEach
    public void setup() {
        this.bookService = new BookService(validator, mapper, repository);
    }

    @DisplayName("Should save book when is valid")
    @Test
    public void save_shouldSaveBookWhenIsValid() {

        var bookDTO = MockHelper.oneBookDTO();

        Mockito.doNothing().when(validator).validate(Mockito.any(BookDTO.class));

        // ver o que eh melhor fazer nada ou chamar o metodo real
        // BDDMockito.when(validator.validate(Mockito.any(BookDTO.class)))
        //             .thenCallRealMethod();

        Mockito.doNothing().when(repository).existsByIsbn(Mockito.anyString());

        var bookMapper = MockHelper.oneBook();
        Mockito.when(mapper.map(bookDTO, Book.class)).thenReturn(bookMapper);

        var savedBook = MockHelper.savedBook();
        Mockito.when(repository.save(any(Book.class))).thenReturn(savedBook);

        var savedBookID = bookService.save(bookDTO);

        Assertions.assertThat(savedBookID).isNotNull();

        Mockito.verify(validator, Mockito.times(1))
                .validate(any(BookDTO.class));

        Mockito.verify(repository, Mockito.times(1))
                .existsByIsbn(anyString());

        Mockito.verify(mapper, Mockito.times(1))
                .map(bookDTO, Book.class);

        Mockito.verify(repository, Mockito.times(1))
                .save(any(Book.class));

    }

    @DisplayName("Should throw exception when title is blank")
    @Test
    public void save_shouldThrowExceptionWhenTitleIsBlank() {
        var messageError = "O title não deve estar em branco";

        var bookDTO = MockHelper.oneBookDTO();
        bookDTO.setTitle("");

        Mockito.doThrow(new ValidationException(messageError))
                .when(validator)
                .validate(Mockito.any(BookDTO.class));

        Assertions.assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> bookService.save(bookDTO));

        Mockito.verify(validator, Mockito.times(1))
                .validate(Mockito.any(BookDTO.class));

        Mockito.verify(repository, Mockito.never())
                .existsByIsbn(anyString());

        Mockito.verify(mapper, Mockito.never())
                .map(bookDTO, Book.class);

        Mockito.verify(repository, Mockito.never())
                .save(any(Book.class));

    }

    @DisplayName("Should throw exception when isbn already registered")
    @Test
    public void save_shouldThrowExceptionWhenIsbnAlreadyRegistered() {

        var messageError = "The field isbn already registered";

        var bookDTO = MockHelper.oneBookDTO();

        doNothing().when(validator).validate(Mockito.any(BookDTO.class));

        Mockito.doThrow(new BusinessException(messageError))
                .when(repository)
                .existsByIsbn(Mockito.anyString());

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> bookService.save(bookDTO))
                .withMessage(messageError);

        // outra forma de fazer o codigo acima

//        BusinessException exception =
//                assertThrows(BusinessException.class, () -> bookService.save(bookDTO));
//
//        assertThat(exception.getMessage()).isEqualTo("The field isbn already registered");

        Mockito.verify(validator, Mockito.times(1))
                .validate(bookDTO, Book.class);

        Mockito.verify(repository, Mockito.times(1))
                .existsByIsbn(anyString());

        Mockito.verify(mapper, Mockito.never())
                .map(bookDTO, Book.class);

        Mockito.verify(repository, Mockito.never())
                .save(any(Book.class));

    }


    /**
     * Adicionar os demais métodos do CRUD
     */
}
