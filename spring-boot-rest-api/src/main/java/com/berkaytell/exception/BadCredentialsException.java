package com.berkaytell.exception;

import com.berkaytell.configuration.ConstantErrorMessages;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
        super(ConstantErrorMessages.BAD_CREDENTIALS);
    }
}
