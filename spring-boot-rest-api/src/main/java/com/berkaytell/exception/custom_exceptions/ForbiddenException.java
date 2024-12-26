package com.berkaytell.exception.custom_exceptions;

import com.berkaytell.configuration.ConstantErrorMessages;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException() {
        super(ConstantErrorMessages.ACCESS_DENIED);
    }
}
