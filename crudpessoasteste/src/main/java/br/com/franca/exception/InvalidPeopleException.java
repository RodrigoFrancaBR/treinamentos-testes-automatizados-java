package br.com.franca.exception;

public class InvalidPeopleException extends RuntimeException {

    public InvalidPeopleException(String message) {
        super(message);
    }
}
