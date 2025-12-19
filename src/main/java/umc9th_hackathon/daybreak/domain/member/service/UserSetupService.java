package umc9th_hackathon.daybreak.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.member.dto.req.UserSetupRequest;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
import umc9th_hackathon.daybreak.domain.mission.entity.Category;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.repository.CategoryRepository;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.MemberErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSetupService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MissionSelectionRepository missionSelectionRepository;

    public void setupByEmail(String email, UserSetupRequest req) {

        Member member = memberRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));

        Category category = categoryRepository.findByCategoryName(req.getCategory())
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        // 기존 선택이 있으면 업데이트, 없으면 생성
        MissionSelection selection = missionSelectionRepository
                .findByMember_MemberId(member.getMemberId())
                .orElseGet(() -> MissionSelection.create(member, category, req.getGoal()));

        selection.updateSelection(category, req.getGoal());
        missionSelectionRepository.save(selection);
    }
}
