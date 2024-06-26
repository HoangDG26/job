package com.itsol.job.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Onion",
                        email = "hoangdg268.tb@gmail.com",
                        url = "https://hoang.com/course"
                ),
                description = "OpenApi documentation Check Attendances",
                title = "OpenApi specification - HoangDG",
                version = "1.0"
//                ,
//                license = @License(
//                        name = "Licence name",
//                        url = "https://some-url.com"
//                ),
//                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
//                ,
//                @Server(
//                        description = "PROD ENV",
//                        url = "https://aliboucoding.com/course"
//                )
        }
        ,
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@Configuration
public class OpenApiConfig {
}
