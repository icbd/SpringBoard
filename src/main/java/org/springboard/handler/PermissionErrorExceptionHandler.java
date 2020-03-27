package org.springboard.handler;


import org.springboard.dto.ErrorDto;
import org.springboard.exception.PermissionErrorException;
import org.springboard.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PermissionErrorExceptionHandler implements ResponseEntityHelper {

    @ExceptionHandler(PermissionErrorException.class)
    public final ResponseEntity<ErrorDto> handlerPermissionErrorException(PermissionErrorException e) {
        ErrorMessage errorMessage = ErrorMessage.builder().code("1001").message(e.getMessage()).build();
        return buildResponseEntity(HttpStatus.FORBIDDEN, errorMessage);
    }
}
