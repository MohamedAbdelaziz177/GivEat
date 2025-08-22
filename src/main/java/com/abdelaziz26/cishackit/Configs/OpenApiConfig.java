package com.abdelaziz26.cishackit.Configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Zero Hunger API",
                description = "Zero Hunger is a platform that helps people in need to get food," +
                 "it destinates the charities in need to the resturnats that have redundant food",
                contact = @Contact(
                        name = "_Abdelaziz26",
                        email = "mohamecabdelaziz66@gmail.com"
                ),

                version = "1.0",

                termsOfService = "Terms of service .."
        ),
        servers = {
                @Server(
                        description = "Development and Testing environments",
                        url = "http://localhost:8080"
                ),

                @Server(
                        description = "Production environments",
                        url = "To be determined"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "Auth using jwt",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
