package com.berkaytell.security.handlers;

import com.berkaytell.configuration.ConstantErrorMessages;
import com.berkaytell.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Result body = Result.of(false, ConstantErrorMessages.ACCESS_DENIED);

        returnResponse(body, response);
    }

    private void returnResponse(Result result, HttpServletResponse response) throws IOException {
        try {
            response.getWriter().write(objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
