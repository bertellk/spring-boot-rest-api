package com.berkaytell.exception;

import com.berkaytell.configuration.ConstantErrorMessages;
import com.berkaytell.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception, WebRequest webRequest) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ConstantErrorMessages.UNEXPECTED_ERROR);
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        System.err.println(exception.getMessage());

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), HttpStatusCode.valueOf(apiError.getStatus()), webRequest);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception, WebRequest webRequest) {
        Result body = Result.of(false, exception.getMessage());
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.OK, webRequest);
    }

}
