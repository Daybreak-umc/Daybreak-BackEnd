package umc9th_hackathon.daybreak.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.member.service.MemberService;
import umc9th_hackathon.daybreak.domain.mission.dto.req.RandomGoalReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.RandomGoalResDTO;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
import umc9th_hackathon.daybreak.domain.mission.entity.Category;
import umc9th_hackathon.daybreak.domain.mission.repository.CategoryRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;


@Service
@RequiredArgsConstructor
@Transactional
public class RandomGoalService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final UpstageLlmService llmService;
    private final MemberService memberService;

        public RandomGoalResDTO.RandomGoalDTO createRandomGoal(RandomGoalReqDTO request, Authentication authentication) {

        // 멤버 인증 인가 함수 적용 (카카오, jwt 둘 다)
        Member member = memberService.getCurrentMember(authentication);

        // Category 찾기
        Category category = categoryRepository.findByCategoryName(request.category())
                .orElseThrow(() -> new GeneralException(MissionErrorCode.CATEGORY_NOT_FOUND));

        // RandomGoal 생성 및 DB 저장
        return llmService.createRandomGoal(
                request.category(),
                member,
                category
        );
    }
}