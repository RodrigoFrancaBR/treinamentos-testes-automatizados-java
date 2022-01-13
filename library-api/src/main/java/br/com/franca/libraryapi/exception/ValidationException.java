package br.com.franca.libraryapi.exception;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
