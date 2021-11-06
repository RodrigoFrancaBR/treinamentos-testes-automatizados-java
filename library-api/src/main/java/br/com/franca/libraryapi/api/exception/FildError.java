package br.com.franca.libraryapi.api.exception;

import lombok.Getter;

@Getter
public class FildError {

    private String field = "field_1";
    private String defaultMessage="defaultMessage_1";

    public FildError(String field, String defaultMessage) {
        this.field = field;
        this.defaultMessage = defaultMessage;
    }
}
