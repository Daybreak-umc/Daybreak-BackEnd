package umc9th_hackathon.daybreak.domain.mission.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
import umc9th_hackathon.daybreak.domain.member.service.MemberService;
import umc9th_hackathon.daybreak.domain.mission.converter.MissionConverter;
import umc9th_hackathon.daybreak.domain.mission.dto.res.MissionResponse;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionRepository;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.MemberErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionQueryService {

    private final MemberRepository memberRepository;
    private final MissionSelectionRepository missionSelectionRepository;
    private final MissionConverter missionConverter;
    private final MemberService memberService;

    @Transactional
    public MissionResponse.MissionGroupListDto getGroupMissions(Authentication authentication){

        // 멤버 인증 인가 함수 적용 (카카오, jwt 둘 다)
        Member member = memberService.getCurrentMember(authentication);


        List<MissionSelection> selections =
                missionSelectionRepository.findByMemberIdWithMissionAndCategory(member.getMemberId());
        return missionConverter.toMissionGroupListDto(selections);
    }

}
