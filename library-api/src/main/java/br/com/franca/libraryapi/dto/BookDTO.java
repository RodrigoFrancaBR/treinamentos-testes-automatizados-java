package br.com.franca.libraryapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
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
