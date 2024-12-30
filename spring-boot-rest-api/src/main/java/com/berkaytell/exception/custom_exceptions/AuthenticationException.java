package com.berkaytell.exception.custom_exceptions;

import com.berkaytell.configuration.ConstantErrorMessages;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super(ConstantErrorMessages.UNEXPECTED_ERROR);
    }
}
