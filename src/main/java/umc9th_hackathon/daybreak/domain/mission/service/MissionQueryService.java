package umc9th_hackathon.daybreak.domain.mission.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
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

    @Transactional
    public MissionResponse.MissionGroupListDto getGroupMissions(String email){

        Member member = memberRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(()->new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<MissionSelection> selections =
                missionSelectionRepository.findByMemberIdWithMissionAndCategory(member.getMemberId());
        return missionConverter.toMissionGroupListDto(selections);
    }

}
