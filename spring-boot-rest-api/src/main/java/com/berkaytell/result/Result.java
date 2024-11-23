package com.berkaytell.result;

import com.berkaytell.configuration.ConstantMessage;

public class Result implements IResult{
    private String message;
    private final boolean success;

    public Result(boolean success) {
        this.success = success;
        this.message = success ? ConstantMessage.MESSAGE_SUCCESS : ConstantMessage.MESSAGE_FAILURE;
    }

    public Result (boolean success, String message) {
        this(success);
        this.message = message;
    }

    public static Result of(boolean success) {
        return new Result(success);
    }

    public static Result of(boolean success, String message) {
        return new Result(success, message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
    }
}