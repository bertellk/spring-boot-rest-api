package com.berkaytell.exception.custom_exceptions;

import com.berkaytell.configuration.ConstantErrorMessages;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super(ConstantErrorMessages.UNAUTHORIZED);
    }
}
