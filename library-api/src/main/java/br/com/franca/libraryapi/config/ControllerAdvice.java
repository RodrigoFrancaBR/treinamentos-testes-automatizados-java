package br.com.franca.libraryapi.config;

import br.com.franca.libraryapi.domain.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError argumentValidationError(MethodArgumentNotValidException exception) {

        BindingResult bindingResult = exception.getBindingResult();

        return new ApiError(bindingResult.getFieldErrors());

    }
}
