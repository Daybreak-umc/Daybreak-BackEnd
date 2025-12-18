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

@Service
@RequiredArgsConstructor
@Transactional
public class UserSetupService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MissionSelectionRepository missionSelectionRepository;

    public void setup(Long memberId, UserSetupRequest req) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Category category = categoryRepository.findByCategoryName(req.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        MissionSelection selection = missionSelectionRepository
                .findByMember_MemberId(memberId)
                .orElseGet(() -> MissionSelection.create(member, category, req.getGoal()));

        selection.updateSelection(category, req.getGoal());

        missionSelectionRepository.save(selection);

    }
}

