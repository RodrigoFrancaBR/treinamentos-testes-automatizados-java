package br.com.franca.libraryapi.domain.repository;

import br.com.franca.libraryapi.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book, Long> {
}
