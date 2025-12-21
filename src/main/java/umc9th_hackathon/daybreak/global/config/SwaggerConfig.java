package umc9th_hackathon.daybreak.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${server.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public OpenAPI swagger() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project")
                        .description("Project Swagger - Umc9th_Hackathon Daybreak")
                        .version("0.0.1"))
                .addServersItem(new Server().url("/"))

                // JWT Scheme
                .addSecurityItem(new SecurityRequirement().addList("JWT"))

                // OAuth2 Scheme (Scopes 없이) - 카카오 요청에 따라 이메일과 같은 사용자 정보 Scope 추가 가능
                .addSecurityItem(new SecurityRequirement().addList("OAuth2"))

                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))

                        .addSecuritySchemes("OAuth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl(baseUrl + "/oauth2/authorization/kakao")
                                                .tokenUrl("https://kauth.kakao.com/oauth/token")))));
    }
}
