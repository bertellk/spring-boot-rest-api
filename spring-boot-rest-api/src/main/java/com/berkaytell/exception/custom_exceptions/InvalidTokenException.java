package com.berkaytell.exception.custom_exceptions;

import com.berkaytell.configuration.ConstantErrorMessages;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super(ConstantErrorMessages.INVALID_TOKEN);
    }
}
