package umc9th_hackathon.daybreak.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpenAiMissionClient {

    private final RestTemplate restTemplate;

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    public List<String> generateWeeklyMissions(String category, String goal) {

        // “3개만, 구체적으로, 짧게”
        String prompt = """
                You are a helpful assistant that creates weekly missions.
                Category: %s
                Goal: %s
                Return exactly 3 missions in Korean.
                Each mission must be specific, actionable, and short (max 20 words).
                Output format must be a JSON array of 3 strings. Example: ["...", "...", "..."]
                """.formatted(category, goal);

        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.7
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> res = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                Map.class
        );

        // 응답 파싱: choices[0].message.content 가 JSON array 문자열임
        Map<?, ?> resBody = res.getBody();
        if (resBody == null) throw new IllegalStateException("OpenAI response body is null");

        List<?> choices = (List<?>) resBody.get("choices");
        Map<?, ?> first = (Map<?, ?>) choices.get(0);
        Map<?, ?> message = (Map<?, ?>) first.get("message");
        String content = (String) message.get("content");

        // content가 '["a","b","c"]' 형태라 가정하고 아주 간단 파싱
        return SimpleJsonArrayParser.parse(content);
    }
}
