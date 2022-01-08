package br.com.franca.libraryapi.api.service;

import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.repository.IBookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @MockBean
    IBookRepository repository;

    IBookService bookService;

    @BeforeEach
    public void setup() {
        this.bookService = new BookService(repository);
    }

    @DisplayName("Should save book when is valid")
    @Test
    public void save_shouldSaveBookWhenIsValid() {

        Book book = Book.builder()
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();

        Book savedBook = Book.builder().id(1l)
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();

        BDDMockito.when(repository.save(book)).thenReturn(savedBook);

        Book saveBook = bookService.save(book);

        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getId()).isEqualTo(savedBook.getId());
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
        Assertions.assertThat(savedBook.getIsbn()).isEqualTo(book.getIsbn());

    }
}
