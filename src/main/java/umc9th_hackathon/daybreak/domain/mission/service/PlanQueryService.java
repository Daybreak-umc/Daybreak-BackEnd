package umc9th_hackathon.daybreak.domain.mission.service;
import org.springframework.security.core.Authentication;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc9th_hackathon.daybreak.domain.mission.converter.PlanConverter;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.entity.Plan;
import umc9th_hackathon.daybreak.domain.mission.exception.PlanErrorCode;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;


@Service
@RequiredArgsConstructor
@Transactional
public class PlanQueryService {
    private final MissionSelectionRepository missionSelectionRepository;

    public PlanResDTO.PlanDto getPlan(Authentication authentication, Long missionSelectionId) {
        // 1. 인증 객체 null 체크 (토큰이 없으면 null입니다)
        if (authentication == null) {
            throw new GeneralException(GeneralErrorCode.UNAUTHORIZED);
        }

        // 2. 파라미터 존재 검증
        if (missionSelectionId == null) {
            throw new GeneralException(PlanErrorCode.NO_PLANID);
        }

        // 3. DB 존재 여부 + 소유권 검증 (Optional Filter)
        String email = authentication.getName();

        MissionSelection missionSelection = missionSelectionRepository.findById(missionSelectionId)
                .filter(ms -> ms.getMember().getEmail().equals(email))
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        Plan plan = missionSelection.getPlan();

        return PlanConverter.toPlanDto(plan);
    }
}
