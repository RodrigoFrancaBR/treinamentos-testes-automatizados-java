package br.com.franca.libraryapi.api.exception;

import br.com.franca.libraryapi.exception.BusinessException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@Setter
//@NoArgsConstructor
@Getter
public class ApiErrors {
    private List<String> errors;
    // private ArrayList<FildError> errors = new ArrayList<>();

        public ApiErrors(BindingResult bindingResult) {
        errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
    }

    public ApiErrors(BusinessException exception) {

            errors = Arrays.asList(exception.getMessage());
    }

//    public ApiErrors(List<ObjectError> allErrors){
//        errors = new ArrayList<>();
//        allErrors.forEach(error -> errors.add(error.getDefaultMessage()));
//    }

//    public ApiErrors(List<FieldError> fieldErrors){
//        errors = new ArrayList<>();
//        fieldErrors.forEach(error -> errors.add(new FildError(error.getField(), error.getDefaultMessage())));
//    }

}
