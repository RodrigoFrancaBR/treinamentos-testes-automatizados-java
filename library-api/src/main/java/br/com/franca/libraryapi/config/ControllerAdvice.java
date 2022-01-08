package br.com.franca.libraryapi.config;

import br.com.franca.libraryapi.domain.ApiError;
import br.com.franca.libraryapi.exception.BusinessException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ApiError businessError(BusinessException exception) {
        return new ApiError(exception.getMessageError());
    }
}
