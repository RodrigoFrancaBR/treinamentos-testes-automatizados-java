package br.com.franca.libraryapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Data
public class BookDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String author;
    private String isbn;
}
