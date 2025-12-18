package umc9th_hackathon.daybreak.domain.mission.dto.res;

import lombok.Builder;

import java.util.Map;

@Builder
public record PlanResDTO(
        String category,
        String goal,
        Map<String, String> plan
) {}