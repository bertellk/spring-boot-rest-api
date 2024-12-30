package com.berkaytell.exception.custom_exceptions;

import com.berkaytell.configuration.ConstantErrorMessages;

public class DisabledUserException extends RuntimeException {
    public DisabledUserException() {
        super(ConstantErrorMessages.DISABLED_USER);
    }
}
