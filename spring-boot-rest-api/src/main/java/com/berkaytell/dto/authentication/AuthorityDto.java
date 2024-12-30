package com.berkaytell.dto.authentication;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorityDto {
    private String authAction;
    private String authPage;
    private String endpoint;
}
