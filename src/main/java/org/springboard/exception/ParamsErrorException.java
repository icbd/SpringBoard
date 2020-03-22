package org.springboard.exception;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class ParamsErrorException extends RuntimeException implements Supplier<ParamsErrorException> {
    private String message;

    public ParamsErrorException() {
        this.message = "Params Error";
    }

    public ParamsErrorException(String message) {
        this.message = message;
    }

    @Override
    public ParamsErrorException get() {
        return this;
    }
}
