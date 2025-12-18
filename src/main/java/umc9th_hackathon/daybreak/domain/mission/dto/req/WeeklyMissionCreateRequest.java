package umc9th_hackathon.daybreak.domain.mission.dto.req;

import jakarta.validation.constraints.NotBlank;

public record WeeklyMissionCreateRequest(
        @NotBlank String category,
        @NotBlank String goal
) {}
