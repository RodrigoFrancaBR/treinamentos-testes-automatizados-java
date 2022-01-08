package br.com.franca.libraryapi.api.repository;

import br.com.franca.libraryapi.api.helper.MockHelper;
import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.repository.IBookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IBookRepository repository;

    @DisplayName("Should return true when isbn already exists")
    @Test
    public void existsByIsbn_shouldReturnTrueWhenIsbnAlreadyExists() {
        Book book = MockHelper.oneBook();
        book.setId(null);
        entityManager.persist(book);
        boolean exists = repository.existsByIsbn(book.getIsbn());
        Assertions.assertThat(exists).isTrue();
    }

    @DisplayName("Should return false when isbn is not exists")
    @Test
    public void existsByIsbn_shouldReturnFalseWhenIsbnNotExists() {
        Book book = MockHelper.oneBook();
        boolean exists = repository.existsByIsbn(book.getIsbn());
        Assertions.assertThat(exists).isFalse();
    }
}
