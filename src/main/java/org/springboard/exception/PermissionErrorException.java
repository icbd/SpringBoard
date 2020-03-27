package org.springboard.exception;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class PermissionErrorException extends RuntimeException implements Supplier<PermissionErrorException> {
    private String message;

    public PermissionErrorException() {
        this.message = "Permission Error";
    }

    public PermissionErrorException(String message) {
        this.message = message;
    }

    @Override
    public PermissionErrorException get() {
        return this;
    }
}
