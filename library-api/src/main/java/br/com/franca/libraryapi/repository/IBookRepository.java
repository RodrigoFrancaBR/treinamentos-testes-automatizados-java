package br.com.franca.libraryapi.repository;

import br.com.franca.libraryapi.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    Optional<Boolean> existsByIsbn(String isbn);
}
