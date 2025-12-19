package umc9th_hackathon.daybreak.domain.mission.converter;

import org.springframework.stereotype.Component;
import umc9th_hackathon.daybreak.domain.mission.dto.res.MissionResponse;
import umc9th_hackathon.daybreak.domain.mission.entity.Mission;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MissionConverter {
    public MissionResponse.MissionGroupListDto toMissionGroupListDto(List<MissionSelection> selections) {

        List<MissionResponse.MissionGroupDto> groupDtos = selections.stream()
                .map(sel -> new MissionResponse.MissionGroupDto(
                        sel.getCategory().getCategoryName(),          // 1. category
                        sel.getMemberMissions().stream()
                                .map(Mission::getContent)
                                .collect(Collectors.toList()),         // 2. missions
                        sel.getObjective(),                           // 3. objective (엔티티에서 바로 추출)
                        sel.getMissionSelectionId()                               // 4. planId (엔티티에서 바로 추출)
                ))
                .collect(Collectors.toList());

        return new MissionResponse.MissionGroupListDto(groupDtos);
    }

    public MissionResponse.MissionCompleteDto toMissionCompleteDto(Mission mission) {
        return new MissionResponse.MissionCompleteDto(
                mission.getMissionId(),
                mission.getUpdateTime()
        );
    }
}
