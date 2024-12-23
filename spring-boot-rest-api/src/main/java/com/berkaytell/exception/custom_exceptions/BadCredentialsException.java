package com.berkaytell.exception.custom_exceptions;

import com.berkaytell.configuration.ConstantErrorMessages;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
        super(ConstantErrorMessages.BAD_CREDENTIALS);
    }
}
