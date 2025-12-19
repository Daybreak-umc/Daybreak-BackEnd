package umc9th_hackathon.daybreak.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class UpstageConfig {
    @Value("${upstage.api-key}")
    private String apiKey;

    @Bean
    public WebClient upstageWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.upstage.ai/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
