package umc9th_hackathon.daybreak.domain.mission.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpenAiMissionClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openai.api.key}") // ✅ 너 yml이 openai.api.key: ${OPENAI_API_KEY} 이거라서 점(.)이 맞음
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    public List<String> generateWeeklyMissions(String category, String goal) {

        // ✅ json_object로 받을 거라서 프롬프트도 객체 형태로 강제
        String prompt = """
                You are a helpful assistant that creates weekly missions.

                Category: %s
                Goal: %s

                Return exactly 3 missions in Korean.
                Each mission must be specific, actionable, and short (max 20 words).

                Output MUST be a JSON object with the following schema:
                {
                  "missions": ["string", "string", "string"]
                }
                """.formatted(category, goal);

        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "response_format", Map.of("type", "json_object"),
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

        Map<?, ?> resBody = res.getBody();
        if (resBody == null) throw new IllegalStateException("OpenAI response body is null");

        List<?> choices = (List<?>) resBody.get("choices");
        if (choices == null || choices.isEmpty()) throw new IllegalStateException("OpenAI choices is empty");

        Map<?, ?> first = (Map<?, ?>) choices.get(0);
        Map<?, ?> message = (Map<?, ?>) first.get("message");
        String content = (String) message.get("content");

        return parseMissionsFromJsonObject(content);
    }

    private List<String> parseMissionsFromJsonObject(String content) {
        try {
            JsonNode root = objectMapper.readTree(content);

            JsonNode missionsNode = root.get("missions");
            if (missionsNode == null || !missionsNode.isArray()) {
                throw new IllegalStateException("OpenAI content.missions is not a JSON array: " + content);
            }

            List<String> missions = new ArrayList<>();
            for (JsonNode n : missionsNode) {
                missions.add(n.asText());
            }

            if (missions.size() != 3) {
                throw new IllegalStateException("OpenAI returned " + missions.size() + " missions, expected 3: " + content);
            }

            return missions;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to parse OpenAI JSON: " + content, e);
        }
    }
}
