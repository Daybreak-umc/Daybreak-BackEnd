package umc9th_hackathon.daybreak.domain.mission.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.entity.Plan;
import umc9th_hackathon.daybreak.domain.mission.exception.InvalidCategoryGoalException;
import umc9th_hackathon.daybreak.domain.mission.repository.PlanRepository;

@Service
public class UpstageLlmService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PlanRepository planRepository;

    public UpstageLlmService(WebClient upstageWebClient, PlanRepository planRepository) {
        this.webClient = upstageWebClient;
        this.planRepository = planRepository;
    }

    @Transactional
    public PlanResDTO.PlanDto getCleanPlan(String category, String goal, MissionSelection missionSelection) {
        // 1. 카테고리/Goal 관련성 검증
        validateCategoryGoal(category, goal);

        List<Map<String, Object>> messages = List.of(
                Map.of("role", "system", "content",
                        """
                        당신은 %s 분야 전문 코치입니다. 사용자가 %s 목표를 달성하도록
                        사용자가 %s 목표를 달성하도록 다음 JSON 형식으로 ‘감정이 느껴지는 짧은 문장’으로 작성하세요.
                        각 문장은 80자 이내로, 자연스럽고 사람의 말처럼 따뜻하게 표현해주세요.
                        숫자나 수치를 나열하지 말고, 변화의 감정과 일상의 변화를 중심으로 작성하세요.

                        
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
                        """.formatted(category, goal, goal, goal, goal, goal, goal, goal)
                ),
                Map.of("role", "user", "content",
                        "%s 분야에서 %s이 되는 내 모습을 상상해서 만들어주세요.".formatted(category, goal))
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
        Map<String, String> planMap = parseTimeline(content);

        // Plan 엔티티 생성 및 DB 저장
        Plan plan = Plan.builder()
                .weekPlan(planMap.get("1주일"))
                .month1Plan(planMap.get("1개월"))
                .month3Plan(planMap.get("3개월"))
                .month6Plan(planMap.get("6개월"))
                .yearPlan(planMap.get("1년"))
                .missionSelection(missionSelection)
                .build();

        Plan savedPlan = planRepository.save(plan);

        // PlanResponse.PlanDto로 변환하여 반환
        return new PlanResDTO.PlanDto(
                savedPlan.getWeekPlan(),
                savedPlan.getMonth1Plan(),
                savedPlan.getMonth3Plan(),
                savedPlan.getMonth6Plan(),
                savedPlan.getYearPlan(),
                savedPlan.getCreateTime()
        );
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