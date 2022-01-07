package br.com.franca.libraryapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Data
public class Book {
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String author;
    private String isbn;
}
