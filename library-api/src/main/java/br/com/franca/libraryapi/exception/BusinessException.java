package br.com.franca.libraryapi.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String errorMenssage) {
        super(errorMenssage);
    }
}
