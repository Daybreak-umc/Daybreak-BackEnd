package umc9th_hackathon.daybreak.domain.mission.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenAiMissionClient {

    @Value("${openai.api.key:}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * category/goal 기반으로 3개 미션 문장만 생성해서 반환
     */
    public List<String> generateWeeklyMissions(String category, String goal) {
        // 키 없으면 바로 fallback 또는 에러 처리 (선택)
        if (apiKey == null || apiKey.isBlank()) {
            return fallback(goal);
        }

        try {
            String url = "https://api.openai.com/v1/responses";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // “3줄만, JSON 배열로” 강제하면 파싱이 쉬움
            String prompt = """
                    너는 습관 형성 코치야.
                    목표 카테고리: %s
                    사용자의 목표: %s

                    조건:
                    - 한국어로 작성
                    - 실천 가능한 "이번 주 행동 미션" 3개
                    - 각 항목은 25자 이내로 짧게
                    - 결과는 반드시 JSON 배열 문자열만 출력 (예: ["...", "...", "..."])
                    """.formatted(category, goal);

            String body = """
                    {
                      "model": "gpt-4.1-mini",
                      "input": [{"role":"user","content":[{"type":"text","text":%s}]}]
                    }
                    """.formatted(objectMapper.writeValueAsString(prompt));

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (!res.getStatusCode().is2xxSuccessful() || res.getBody() == null) {
                return fallback(goal);
            }

            // Responses API의 output_text 뽑기 (가장 단순한 방식)
            JsonNode root = objectMapper.readTree(res.getBody());

            // 응답 구조가 바뀔 수 있어서, 안전하게 "output_text" 또는 텍스트 후보를 탐색
            String text = null;
            if (root.has("output_text")) {
                text = root.get("output_text").asText();
            } else {
                // fallback 탐색(최소 방어)
                text = root.toString();
            }

            // text는 JSON 배열 문자열이어야 함: ["a","b","c"]
            JsonNode arr = objectMapper.readTree(text);
            if (!arr.isArray() || arr.size() != 3) {
                return fallback(goal);
            }

            List<String> missions = new ArrayList<>();
            for (JsonNode n : arr) missions.add(n.asText().trim());
            return missions;

        } catch (Exception e) {
            // 여기서 바로 실패로 던져도 되고, UX 위해 fallback도 가능
            return fallback(goal);
        }
    }

    private List<String> fallback(String goal) {
        // API 실패 시 최소 보장(서버 죽지 않게)
        return List.of(
                goal + " 관련 작은 행동 1개",
                goal + " 10분만 실행하기",
                goal + " 체크리스트 작성하기"
        );
    }
}
