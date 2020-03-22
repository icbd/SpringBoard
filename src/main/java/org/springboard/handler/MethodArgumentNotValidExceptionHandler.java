package org.springboard.handler;

import org.apache.commons.lang3.StringUtils;
import org.springboard.dto.ErrorDto;
import org.springboard.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handle Exception: MethodArgumentNotValidException
 */
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorDto errorResponse = ErrorDto.builder()
                                         .errors(errorMessagesOf(exception))
                                         .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private List<ErrorMessage> errorMessagesOf(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(e -> ErrorMessage.builder().code("1004").message(assembleMessage(e)).build())
                        .collect(Collectors.toList());
    }

    /**
     * 从对象中提取错误提示
     *
     * @param error
     * @return
     */
    private String assembleMessage(ObjectError error) {
        if (error instanceof FieldError) {
            String fieldName = StringUtils.capitalize(((FieldError) error).getField());
            return (fieldName + " " + error.getDefaultMessage());
        }

        return "";
    }
}
