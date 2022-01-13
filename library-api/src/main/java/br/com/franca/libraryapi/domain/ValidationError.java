package br.com.franca.libraryapi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ValidationError {
    private String error;

    public ValidationError(String messageError) {
        this.error = messageError;
    }
}
