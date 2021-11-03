package br.com.franca.libraryapi.helper.mock;

import br.com.franca.libraryapi.api.dto.BookDTO;
import br.com.franca.libraryapi.model.entity.Book;

public class MockHelper {

    public static Book oneBook(){
        return Book.builder()
                .id(1l)
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();
    }

    public static BookDTO oneBookDtoIn(){

        return BookDTO.builder()
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();
    }

    public static BookDTO oneBookDtoOut(){

        return BookDTO.builder()
                .id(1l)
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();
    }

}
