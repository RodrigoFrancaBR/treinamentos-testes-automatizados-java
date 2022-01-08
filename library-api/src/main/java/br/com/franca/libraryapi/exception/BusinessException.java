package br.com.franca.libraryapi.exception;

import br.com.franca.libraryapi.domain.MessageError;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private MessageError messageError;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(MessageError messageError) {
        super(messageError.getMessage());
        this.messageError = messageError;
    }
}
