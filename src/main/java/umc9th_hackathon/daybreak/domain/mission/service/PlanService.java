package umc9th_hackathon.daybreak.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
import umc9th_hackathon.daybreak.domain.mission.dto.req.PlanReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.entity.Category;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.repository.CategoryRepository;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.MemberErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;
import umc9th_hackathon.daybreak.global.security.MemberDetails;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final UpstageLlmService llmService;
    private final MissionSelectionRepository missionSelectionRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public PlanResDTO.PlanDto createPlan(PlanReqDTO request, String email) {

        // 현재 인증된 사용자 정보 가져오기
        Member member = memberRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
        
        // Category 찾기
        Category category = categoryRepository.findByCategoryName(request.category())
                .orElseThrow(() -> new GeneralException(MissionErrorCode.CATEGORY_NOT_FOUND));
        
        // MissionSelection 중복 체크
        missionSelectionRepository
                .findByMemberAndCategoryAndObjective(member, category, request.goal())
                .ifPresent(ms -> {
                    throw new GeneralException(MissionErrorCode.DUPLICATE_GOAL);
                });
        
        // MissionSelection 생성
        MissionSelection missionSelection = MissionSelection.builder()
                .objective(request.goal())
                .member(member)
                .category(category)
                .build();
        
        MissionSelection savedMissionSelection = missionSelectionRepository.save(missionSelection);
        
        // Plan 생성 및 DB 저장
        return llmService.getCleanPlan(
                request.category(), 
                request.goal(), 
                savedMissionSelection
        );
    }
}

