package br.com.franca.libraryapi.api.helper;

import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dtos.BookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MockHelper {

    private MockHelper() {
    }

    private static ObjectMapper mapper;

    public static ObjectMapper oneMapper() {

        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        return mapper;
    }

    public static String of(Object object) throws Exception {
        return oneMapper().writeValueAsString(object);
    }

    public static <T> Object of(String json, Class<T> objectClass) throws Exception {
        return oneMapper().readValue(json, objectClass);
    }

    public static BookDTO oneBookDTO() {
        return BookDTO.builder()
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();
    }

    public static Book oneBook() {

        return Book.builder()
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();
    }

    public static Book savedBook() {
        Book book = oneBook();
        book.setId(1l);
        return book;
    }


    public static BookDTO savedBookDTO() {
        BookDTO bookDTO = oneBookDTO();
        bookDTO.setId(1L);
        return bookDTO;
    }
}
