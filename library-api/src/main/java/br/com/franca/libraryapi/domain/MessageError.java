package br.com.franca.libraryapi.domain;

import lombok.Data;

@Data
public class MessageError {

    private String field;
    private String message;

    public MessageError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public MessageError(String messageError) {
        this.message = messageError;
    }
}
