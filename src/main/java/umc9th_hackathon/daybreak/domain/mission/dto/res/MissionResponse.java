package umc9th_hackathon.daybreak.domain.mission.dto.res;

import umc9th_hackathon.daybreak.domain.mission.entity.Mission;

import java.util.List;

public class MissionResponse {

    // List(카테고리 1개 + 미션들) 묶음 여러 개
    public record MissionGroupListDto(
            List<MissionGroupDto> missionGroups
    ){}

    // 카테고리 1개 + 해당 카테고리의 미션들
    public record MissionGroupDto(
            String category,
            List<String> missions
    ) {}
}
