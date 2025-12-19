package umc9th_hackathon.daybreak.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.mission.converter.MissionConverter;
import umc9th_hackathon.daybreak.domain.mission.dto.res.MissionResponse;
import umc9th_hackathon.daybreak.domain.mission.entity.Mission;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.exception.MissionErrorCode;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionRepository;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;
import umc9th_hackathon.daybreak.domain.mission.repository.PlanRepository;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandService {

    private final MissionConverter missionConverter;
    private final MissionRepository missionRepository;
    private final MissionSelectionRepository missionSelectionRepository;
    private final PlanRepository planRepository;

    /** 미션 완료 체크 */
    public MissionResponse.MissionCompleteDto patchMissionComplete(Long missionId, String email) {

        Mission mission = missionRepository
                .findByMissionIdAndMissionSelection_Member_Email(missionId, email)
                .orElseThrow(() -> new GeneralException(MissionErrorCode.MISSION_NOT_FOUND));

        mission.complete();
        return missionConverter.toMissionCompleteDto(mission);
    }

    /**목표 삭제 */
    public void deleteGoalByEmail(String email) {

        MissionSelection selection = missionSelectionRepository
                .findByMember_Email(email)
                .orElseThrow(() -> new GeneralException(MissionErrorCode.MISSION_SELECTION_NOT_FOUND));

        missionRepository.deleteByMissionSelection(selection);

        planRepository.deleteByMissionSelection(selection);

        missionSelectionRepository.delete(selection);
    }
}
