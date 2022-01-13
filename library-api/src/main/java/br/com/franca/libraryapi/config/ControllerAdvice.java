package br.com.franca.libraryapi.config;

import br.com.franca.libraryapi.domain.ValidationError;
import br.com.franca.libraryapi.domain.ValidationErrorAPI;
import br.com.franca.libraryapi.domain.MessageError;
import br.com.franca.libraryapi.exception.BusinessException;
import br.com.franca.libraryapi.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ValidationErrorAPI argumentValidationError(BindException exception) {

        BindingResult bindingResult = exception.getBindingResult();

        List<MessageError> messageErrorList = bindingResult.getFieldErrors().stream()
                .map(fieldError -> new MessageError(fieldError.getField(),
                        "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ValidationErrorAPI(messageErrorList);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ValidationError businessError(ValidationException exception) {
        return new ValidationError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ValidationError validationError(BusinessException exception) {
        return new ValidationError(exception.getMessage());
    }
}
