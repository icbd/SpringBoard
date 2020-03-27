package org.springboard.handler;

import org.springboard.dto.ErrorDto;
import org.springboard.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class NotFoundExceptionHandler implements ResponseEntityHelper {

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public final ResponseEntity<ErrorDto> handleNotFoundException() {
        ErrorMessage errorMessage = ErrorMessage.builder().code("1004").message("Not Found").build();
        return buildResponseEntity(HttpStatus.NOT_FOUND, errorMessage);
    }
}
