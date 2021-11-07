package br.com.franca.libraryapi.api.exception;

import br.com.franca.libraryapi.api.exception.ApiErrors;
import br.com.franca.libraryapi.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrors handlerValidationException (MethodArgumentNotValidException exception){
//        return new ApiErrors(exception.getBindingResult().getFieldErrors());
//        return new ApiErrors(exception.getBindingResult().getAllErrors());
        return new ApiErrors(exception.getBindingResult());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ApiErrors handBusinessException (BusinessException exception){
//        return new ApiErrors(exception.getBindingResult().getFieldErrors());
//        return new ApiErrors(exception.getBindingResult().getAllErrors());
        return new ApiErrors(exception);
    }
}
