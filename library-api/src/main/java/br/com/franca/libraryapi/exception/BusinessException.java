package br.com.franca.libraryapi.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);

    }
}
