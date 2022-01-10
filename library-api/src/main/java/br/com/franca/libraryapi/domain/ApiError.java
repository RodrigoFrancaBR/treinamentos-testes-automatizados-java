package br.com.franca.libraryapi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class ApiError {

    private List<MessageError> errors;

    public ApiError(List<FieldError> fieldErrors) {
        this.errors = fieldErrors
                .stream()
                .map(e -> new MessageError(e.getField(),
                        "The field " + e.getField() + " " + e.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    public ApiError(MessageError messageError) {
        this.errors = Arrays.asList(messageError);
    }

    public ApiError(MessageError... elements) {
        this.errors = Arrays.stream(elements).collect(Collectors.toList());
    }
}