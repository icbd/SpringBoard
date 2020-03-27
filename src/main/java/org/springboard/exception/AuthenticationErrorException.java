package org.springboard.exception;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class AuthenticationErrorException extends RuntimeException implements Supplier<AuthenticationErrorException> {
    private String message;

    public AuthenticationErrorException() {
        this.message = "Authentication Error";
    }

    public AuthenticationErrorException(String message) {
        this.message = message;
    }

    @Override
    public AuthenticationErrorException get() {
        return this;
    }
}
