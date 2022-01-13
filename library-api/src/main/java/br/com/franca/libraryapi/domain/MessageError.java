package br.com.franca.libraryapi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageError {

    private String field;
    private String message;

    public MessageError(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
