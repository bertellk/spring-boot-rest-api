package com.berkaytell.exception.custom_exceptions;

import com.berkaytell.configuration.ConstantErrorMessages;

public class CustomBadCredentialsException extends RuntimeException {
    public CustomBadCredentialsException() {
        super(ConstantErrorMessages.BAD_CREDENTIALS);
    }
}
