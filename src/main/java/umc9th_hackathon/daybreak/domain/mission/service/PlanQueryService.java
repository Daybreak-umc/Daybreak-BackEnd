package umc9th_hackathon.daybreak.domain.mission.service;
import org.springframework.security.core.Authentication;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.service.MemberService;
import umc9th_hackathon.daybreak.domain.mission.converter.PlanConverter;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.entity.Plan;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.PlanErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;


@Service
@RequiredArgsConstructor
@Transactional
public class PlanQueryService {
    private final MissionSelectionRepository missionSelectionRepository;
    private final MemberService memberService;

    public PlanResDTO.PlanDto getPlan(Authentication authentication, Long missionSelectionId) {

        // 멤버 인증 인가 함수 적용 (카카오, jwt 둘 다)
        Member member = memberService.getCurrentMember(authentication);


        MissionSelection missionSelection = missionSelectionRepository.findById(missionSelectionId)
                .filter(ms -> ms.getMember().getEmail().equals(member.getEmail()))
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        Plan plan = missionSelection.getPlan();

        return PlanConverter.toPlanDto(plan);
    }
}
