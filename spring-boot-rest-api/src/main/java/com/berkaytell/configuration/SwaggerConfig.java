package com.berkaytell.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Course Application API",
                version = "1.0",
                description = "Course Application API Documentation",
                contact = @Contact(
                        name = "Oğuzhan SANCAR",
                        email = "oguzhansancar93@icloud.com",
                        url = "")
        ),
        security = {
                @SecurityRequirement(
                        name = "bearerAuth",
                        scopes = {"read", "write"}
                )
        },
        servers = {
                @Server(
                        description = "Local Server",
                        url = "http://localhost:8080"
                )
        }
)
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "bearerAuth",
                        description = "JWT Token",
                        scheme = "bearer",
                        bearerFormat = "JWT",
                        type = SecuritySchemeType.HTTP,
                        in = SecuritySchemeIn.HEADER
                )
        }
)

// Visit -> http://localhost:8080/swagger-ui/index.html
public class SwaggerConfig {
}
