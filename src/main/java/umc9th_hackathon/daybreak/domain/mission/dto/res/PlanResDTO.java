package umc9th_hackathon.daybreak.domain.mission.dto.res;

import java.time.LocalDateTime;

public class PlanResDTO {
    public record PlanDto(
            String week,
            String month1,
            String month3,
            String month6,
            String year,
            LocalDateTime createTime
    ) {}
}
