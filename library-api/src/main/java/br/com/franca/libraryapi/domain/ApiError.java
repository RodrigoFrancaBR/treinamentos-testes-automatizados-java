package br.com.franca.libraryapi.domain;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ApiError {

    private final List<MessageError> errors;

    public ApiError(List<FieldError> fieldErrors) {
        this.errors = fieldErrors
                .stream()
                .map(e -> new MessageError(e.getField(),
                        "The field " + e.getField() + " " + e.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
