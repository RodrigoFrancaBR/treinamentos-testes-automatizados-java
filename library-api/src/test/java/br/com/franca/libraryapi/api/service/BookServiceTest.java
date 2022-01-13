package br.com.franca.libraryapi.api.service;

import br.com.franca.libraryapi.api.helper.MockHelper;
import br.com.franca.libraryapi.config.ValidatorConfig;
import br.com.franca.libraryapi.domain.MessageError;
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
import org.mockito.BDDMockito;
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

        BookDTO bookDTO = MockHelper.oneBookDTO();

        // ver sobre o doNothing()
        // doNothing().when(validator).validate(Mockito.any(BookDTO.class));

        Book bookMapper = MockHelper.oneBook();
        BDDMockito.when(mapper.map(bookDTO, Book.class)).thenReturn(bookMapper);

        BDDMockito.when(repository.existsByIsbn(anyString())).thenReturn(Boolean.FALSE);

        Book savedBook = MockHelper.savedBook();
        BDDMockito.when(repository.save(any(Book.class))).thenReturn(savedBook);

        BookDTO saveDTOMapper = MockHelper.savedBookDTO();
        BDDMockito.when(mapper.map(savedBook, BookDTO.class)).thenReturn(saveDTOMapper);

        BookDTO saveDTO = bookService.save(bookDTO);

        Assertions.assertThat(saveDTO.getId()).isNotNull();
        Assertions.assertThat(saveDTO.getTitle()).isEqualTo(bookDTO.getTitle());
        Assertions.assertThat(saveDTO.getAuthor()).isEqualTo(bookDTO.getAuthor());
        Assertions.assertThat(saveDTO.getIsbn()).isEqualTo(bookDTO.getIsbn());

        Mockito.verify(validator, Mockito.times(1))
                .validate(any(BookDTO.class));

        Mockito.verify(mapper, Mockito.times(1))
                .map(bookDTO, Book.class);

        Mockito.verify(repository, Mockito.times(1))
                .existsByIsbn(anyString());

        Mockito.verify(repository, Mockito.times(1))
                .save(any(Book.class));

        Mockito.verify(mapper, Mockito.times(1))
                .map(savedBook, BookDTO.class);

    }

    @DisplayName("Should throw exception when isbn already registered")
    @Test
    public void save_shouldThrowExceptionWhenIsbnAlreadyRegistered() {
        BookDTO bookDTO = MockHelper.oneBookDTO();

        // ver sobre o doNothing()
        //  doNothing().when(validator).validate(Mockito.any(BookDTO.class));

        Book bookMapper = MockHelper.oneBook();
        BDDMockito.when(mapper.map(bookDTO, Book.class)).thenReturn(bookMapper);

        BDDMockito.when(repository.existsByIsbn(anyString())).thenReturn(Boolean.TRUE);

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> bookService.save(bookDTO))
                .withMessage("The field isbn already registered");

        Mockito.verify(mapper, Mockito.times(1))
                .map(bookDTO, Book.class);

        Mockito.verify(repository, Mockito.times(1))
                .existsByIsbn(anyString());

        Mockito.verify(repository, Mockito.never())
                .save(any(Book.class));

        Mockito.verify(mapper, Mockito.never())
                .map(MockHelper.oneBook(), BookDTO.class);
    }

    @DisplayName("Should throw exception when title is blank")
    @Test
    public void save_shouldThrowExceptionWhenTitleIsBlank() {
        String messageError = "O title não deve estar em branco";

        var bookDTO = MockHelper.oneBookDTO();
        bookDTO.setTitle("");

        Mockito.doThrow(new ValidationException(messageError))
                .when(validator)
                .validate(Mockito.any(BookDTO.class));

        Assertions.assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> bookService.save(bookDTO));

        Mockito.verify(validator, Mockito.times(1))
                .validate(Mockito.any(BookDTO.class));

        Mockito.verify(mapper, Mockito.never())
                .map(bookDTO, Book.class);

        Mockito.verify(repository, Mockito.never())
                .existsByIsbn(anyString());

        Mockito.verify(repository, Mockito.never())
                .save(any(Book.class));

        Mockito.verify(mapper, Mockito.never())
                .map(MockHelper.oneBook(), BookDTO.class);
    }

    /**
     * Adicionar os demais métodos do CRUD
     */
}
