package br.com.franca.libraryapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class ValidationErrorAPI {

    private List<MessageError> errors;

    public ValidationErrorAPI(List<MessageError> messageErrorList) {
        this.errors = messageErrorList;
    }

}