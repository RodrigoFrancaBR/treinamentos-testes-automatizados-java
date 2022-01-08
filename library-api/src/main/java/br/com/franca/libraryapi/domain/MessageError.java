package br.com.franca.libraryapi.domain;

import lombok.Data;

@Data
public class MessageError {

    private final String field;
    private final String message;

    public MessageError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
