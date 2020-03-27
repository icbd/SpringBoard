package org.springboard.handler;

import org.springboard.dto.ErrorDto;
import org.springboard.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public interface ResponseEntityHelper {

    default ResponseEntity<ErrorDto> buildResponseEntity(HttpStatus httpStatus, ErrorMessage... errorMessages) {
        return buildResponseEntity(httpStatus, Arrays.asList(errorMessages));
    }

    default ResponseEntity<ErrorDto> buildResponseEntity(HttpStatus httpStatus, List<ErrorMessage> errorMessages) {
        ErrorDto errorDto = ErrorDto.builder()
                                    .errors(errorMessages)
                                    .build();
        return ResponseEntity.status(httpStatus).body(errorDto);
    }
}
