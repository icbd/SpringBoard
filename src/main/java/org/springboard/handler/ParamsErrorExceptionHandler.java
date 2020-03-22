package org.springboard.handler;


import org.springboard.dto.ErrorDto;
import org.springboard.exception.ParamsErrorException;
import org.springboard.model.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParamsErrorExceptionHandler implements ResponseEntityHelper {

    @ExceptionHandler(ParamsErrorException.class)
    public final ResponseEntity<ErrorDto> handlerParamsErrorException(ParamsErrorException e) {
        ErrorMessage errorMessage = ErrorMessage.builder().code("1004").message(e.getMessage()).build();
        return buildResponseEntity(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDto> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorMessage errorMessage = ErrorMessage.builder().code("1004").message("Params Error").build();
        return buildResponseEntity(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
