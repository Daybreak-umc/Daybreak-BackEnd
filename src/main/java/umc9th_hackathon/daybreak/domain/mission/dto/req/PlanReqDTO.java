package umc9th_hackathon.daybreak.domain.mission.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PlanReqDTO(

        @NotBlank(message = "카테고리를 입력하세요")
        String category,

        @NotBlank(message = "목표를 입력하세요")
        String goal
) {}