package umc9th_hackathon.daybreak.domain.mission.dto.res;

import java.time.LocalDateTime;
import java.util.List;

public class WeeklyMissionResDTO {

    public record WeeklyMissionDTO(
            Long objectiveId,
            String objective,
            String category,
            List<String> missions,
            String difficultyLevel,
            LocalDateTime createdAt,
            LocalDateTime expiresAt
    ) {}
}