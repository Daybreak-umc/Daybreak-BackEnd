package umc9th_hackathon.daybreak.domain.mission.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.exception.InvalidCategoryGoalException;

@Service
public class UpstageLlmService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UpstageLlmService(WebClient upstageWebClient) {
        this.webClient = upstageWebClient;
    }

    public PlanResDTO getCleanPlan(String category, String goal) {
        // 1. 카테고리/Goal 관련성 검증
        validateCategoryGoal(category, goal);

        List<Map<String, Object>> messages = List.of(
                Map.of("role", "system", "content",
                        """
                        당신은 %s 분야 전문 코치입니다. 사용자가 %s 목표를 달성하도록
                        다음 JSON 형식으로 1주일, 1개월, 3개월, 6개월, 1년 내 모습을 작성하세요.
                        자연스럽게 문장을 작성해주세요.
                        최대 80자까지 작성할 수 있습니다.

                        
                        JSON만 출력 (이스케이프/추가텍스트 완전 금지):
                        {
                          "1주일": "%s 첫 시작 모습",
                          "1개월": "%s 초기 변화 모습",
                          "3개월": "%s 눈에 띄는 변화 모습",
                          "6개월": "%s 중간 성과 모습",
                          "1년": "%s 최종 완성 모습"
                        }
                        
                        예시 모습 : 운동에 흥미를 느끼기 시작합니다.
                        매주 운동을 나갈 수 있는 힘이 생깁니다.
                        """.formatted(category, goal, goal, goal, goal, goal, goal)
                ),
                Map.of("role", "user", "content",
                        "%s 분야에서 %s이 되는 단계별 로드맵을 만들어주세요.".formatted(category, goal))
        );

        Map<String, Object> request = Map.of(
                "model", "solar-pro2",
                "messages", messages,
                "response_format", Map.of("type", "json_object"),
                "temperature", 0.5,
                "max_tokens", 5000
        );

        String rawResponse = webClient.post()
                .uri("/solar/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String content = extractContent(rawResponse);
        Map<String, String> plan = parseTimeline(content);

        return PlanResDTO.builder()
                .category(category)
                .goal(goal)
                .plan(plan)
                .build();
    }

    private void validateCategoryGoal(String category, String goal) {
        // LLM을 이용한 관련성 검증
        String validationPrompt = """
                "%s" 카테고리와 "%s" 목표가 관련이 있나요? 
                관련이 있으면 "VALID"만 출력하고, 없으면 "INVALID: 이유" 형식으로 출력하세요.
                """.formatted(category, goal);

        Map<String, Object> validationRequest = Map.of(
                "model", "solar-pro2",
                "messages", List.of(Map.of("role", "user", "content", validationPrompt)),
                "temperature", 0.1,
                "max_tokens", 50
        );

        String validationResponse = webClient.post()
                .uri("/solar/chat/completions")
                .bodyValue(validationRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String validationResult = extractValidationContent(validationResponse);

        if (!validationResult.trim().equals("VALID")) {
            throw new InvalidCategoryGoalException(
                    String.format("카테고리 '%s'와 목표 '%s'가 맞지 않습니다: %s",
                            category, goal, validationResult)
            );
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> parseTimeline(String content) {
        try {
            Map<String, Object> planMap = objectMapper.readValue(content, Map.class);
            return planMap.entrySet().stream()
                    .collect(java.util.stream.Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> String.valueOf(entry.getValue())
                    ));
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패: " + content, e);
        }
    }

    private String extractContent(String rawResponse) {
        try {
            JsonNode responseNode = objectMapper.readTree(rawResponse);
            JsonNode contentNode = responseNode
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content");
            return contentNode.asText();
        } catch (Exception e) {
            throw new RuntimeException("응답 파싱 실패: " + rawResponse, e);
        }
    }

    private String extractValidationContent(String rawResponse) {
        try {
            JsonNode responseNode = objectMapper.readTree(rawResponse);
            JsonNode contentNode = responseNode
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content");
            return contentNode.asText();
        } catch (Exception e) {
            return "INVALID: 파싱 오류";
        }
    }
}