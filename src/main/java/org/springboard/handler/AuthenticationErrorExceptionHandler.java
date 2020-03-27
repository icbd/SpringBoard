package org.springboard.handler;


import org.springboard.dto.ErrorDto;
import org.springboard.exception.AuthenticationErrorException;
import org.springboard.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationErrorExceptionHandler implements ResponseEntityHelper {

    @ExceptionHandler(AuthenticationErrorException.class)
    public final ResponseEntity<ErrorDto> handlerAuthenticationErrorException(AuthenticationErrorException e) {
        ErrorMessage errorMessage = ErrorMessage.builder().code("1000").message(e.getMessage()).build();
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, errorMessage);
    }
}
