package umc9th_hackathon.daybreak.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.service.MemberService;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.WeeklyMissionResDTO;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyMissionService {

    private final MissionSelectionRepository missionSelectionRepository;
    private final UpstageLlmService llmService;
    private final MemberService memberService;

    public WeeklyMissionResDTO.WeeklyMissionDTO createWeeklyMissions(
            WeeklyMissionReqDTO request, Authentication authentication) {

        // 1. 사용자 인증
        Member member = memberService.getCurrentMember(authentication);

        // 2. MissionSelection 조회 (사용자 소유 확인)
        MissionSelection missionSelection = missionSelectionRepository.findById(request.mission_selection_id())
                .orElseThrow(() -> new GeneralException(MissionErrorCode.CATEGORY_NOT_FOUND));

        // 3. 소유권 확인
        if (!missionSelection.getMember().getMemberId().equals(member.getMemberId())) {
            throw new GeneralException(MissionErrorCode.CATEGORY_NOT_FOUND);
        }

        // 4. AI로 주간 미션 생성
        return llmService.createWeeklyMissions(missionSelection);
    }
}