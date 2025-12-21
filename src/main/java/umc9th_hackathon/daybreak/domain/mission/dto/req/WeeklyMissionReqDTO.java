package umc9th_hackathon.daybreak.domain.mission.dto.req;

import jakarta.validation.constraints.NotNull;

public record WeeklyMissionReqDTO(
        @NotNull(message = "목표 ID는 필수입니다.")
        Long objectiveId
) {
}