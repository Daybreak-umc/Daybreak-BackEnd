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
                        sel.getCategory().getCategoryName(),          // 카테고리 이름
                        sel.getMemberMissions().stream()
                                .map(Mission::getContent)
                                .collect(Collectors.toList())          // Mission 리스트
                ))
                .collect(Collectors.toList());

        return new MissionResponse.MissionGroupListDto(groupDtos);
    }
}
