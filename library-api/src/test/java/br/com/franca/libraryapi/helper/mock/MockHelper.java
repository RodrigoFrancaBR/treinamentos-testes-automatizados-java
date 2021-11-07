package br.com.franca.libraryapi.helper.mock;

import br.com.franca.libraryapi.api.dto.BookDTO;
import br.com.franca.libraryapi.api.exception.ApiErrors;
import br.com.franca.libraryapi.api.exception.FildError;
import br.com.franca.libraryapi.model.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockHelper {

    public static Book oneBook(){
        return Book.builder()
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();
    }

    public static BookDTO oneBookDTO(){
        return BookDTO.builder()
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();
    }


//    public static ApiErrors oneApiErros() {
//        FildError errorAuthor = new FildError("author", "must not be empty");
//        FildError errorIsbn = new FildError("isbn", "must not be empty");
//        FildError errorTitle = new FildError("title", "must not be empty");
//
//        ArrayList<FildError> errors = new ArrayList();
//
//        errors.add(errorAuthor);
//        errors.add(errorIsbn);
//        errors.add(errorTitle);
//
//        ApiErrors apiErrors = new ApiErrors();
//        // List<FildError> collect = errors.stream().sorted().collect(Collectors.toList());
//        // apiErrors.setErrors(new ArrayList<>(collect));
//        apiErrors.setErrors(errors);
//        return apiErrors;
//    }

}
