package umc9th_hackathon.daybreak.domain.mission.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RandomGoalReqDTO(

        @NotBlank(message = "카테고리를 입력하세요")
        String category

) {}