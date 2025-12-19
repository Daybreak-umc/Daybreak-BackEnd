package umc9th_hackathon.daybreak.domain.mission.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc9th_hackathon.daybreak.domain.mission.converter.MissionConverter;
import umc9th_hackathon.daybreak.domain.mission.dto.res.MissionResponse;
import umc9th_hackathon.daybreak.domain.mission.entity.Mission;
import umc9th_hackathon.daybreak.domain.mission.exception.MissionErrorCode;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandService {

    private final MissionConverter missionConverter;
    private final MissionRepository missionRepository;

    @Transactional
    public MissionResponse.MissionCompleteDto patchMissionComplete(Long missionId,String email) {

        Mission mission = missionRepository
                .findByMissionIdAndMissionSelection_Member_Email(missionId, email)
                .orElseThrow(() -> new GeneralException(MissionErrorCode.MISSION_NOT_FOUND));

        mission.complete();
        return missionConverter.toMissionCompleteDto(mission);
    }
}
