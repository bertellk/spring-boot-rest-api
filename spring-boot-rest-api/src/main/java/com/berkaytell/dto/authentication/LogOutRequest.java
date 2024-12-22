package com.berkaytell.dto.authentication;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogOutRequest {
    private String token;
}
