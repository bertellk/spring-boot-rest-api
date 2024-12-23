package com.berkaytell.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ApiError {
    private int status;
    private String message;
    private Map<String, String> validErrors = new HashMap<>();
}
