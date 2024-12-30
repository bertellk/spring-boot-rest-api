package com.berkaytell.dto.authentication;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogInResponse {
    private String accessToken;
    private String refreshToken;
}
