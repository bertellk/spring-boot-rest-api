package com.berkaytell.dto.authentication;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogInResponse {
    private String accessToken;
    private String refreshToken;
}
