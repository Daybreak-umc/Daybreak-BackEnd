package umc9th_hackathon.daybreak.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final UpstageLlmService llmService;
    private final MissionSelectionRepository missionSelectionRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private static final int MAX_GOALS_PER_USER = 4;


    public PlanResDTO.PlanDto createPlan(PlanReqDTO request, Authentication authentication) {

        if (authentication == null) {
            throw new GeneralException(GeneralErrorCode.UNAUTHORIZED);
        }

        String email = authentication.getName();

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


        // 유저의 현재 목표 개수 확인
        long currentGoalCount = missionSelectionRepository.countByMember(member);
        if (currentGoalCount >= MAX_GOALS_PER_USER) {
            throw new GeneralException(MissionErrorCode.MAX_GOALS_EXCEEDED);
        }
        
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

