package br.com.franca.libraryapi.api.service;

import br.com.franca.libraryapi.domain.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Autowired
    BookService bookService;

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

        bookService.save(book);
        
        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
        Assertions.assertThat(savedBook.getIsbn()).isEqualTo(book.getIsbn());

    }
}
