package br.com.franca.libraryapi.api.helper;

import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dtos.BookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MockHelper {

    private MockHelper() {
    }

    private static ObjectMapper mapper;

    public static String of(BookDTO dto) throws Exception {

        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        return mapper.writeValueAsString(dto);
    }

    public static BookDTO oneBookDTO() {
        return BookDTO.builder()
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();
    }

    public static Book oneBook() {

        return Book.builder().id(1l)
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();
    }
}
