package umc9th_hackathon.daybreak.domain.mission.dto.res;

import java.util.List;

public record WeeklyMissionCreateResponse(
        List<String> missions
) {}
