package com.berkaytell.exception;

import com.berkaytell.configuration.ConstantErrorMessages;
import com.berkaytell.exception.custom_exceptions.*;
import com.berkaytell.result.Result;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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

    @ExceptionHandler({BadCredentialsException.class, InvalidTokenException.class, UnauthorizedException.class, ForbiddenException.class, ExpiredJwtException.class, DisabledException.class})
    public ResponseEntity<Object> handleBadCredentialsException(Exception exception, WebRequest webRequest) {
        Result body = Result.of(false, getCustomErrorMessage(exception));
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.OK, webRequest);
    }

    String getCustomErrorMessage(Exception exception) {
        if (exception instanceof DisabledException)
            return new DisabledUserException().getMessage();
        if (exception instanceof ExpiredJwtException)
            return new InvalidTokenException().getMessage();
        if (exception instanceof ForbiddenException)
            return new ForbiddenException().getMessage();
        if (exception instanceof BadCredentialsException)
            return new CustomBadCredentialsException().getMessage();
        if (exception instanceof MessagingException)
            return new CustomMessagingException().getMessage();

        return ConstantErrorMessages.UNEXPECTED_ERROR;
    }

}
