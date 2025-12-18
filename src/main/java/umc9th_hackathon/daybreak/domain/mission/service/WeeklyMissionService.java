package umc9th_hackathon.daybreak.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionCreateRequest;
import umc9th_hackathon.daybreak.domain.mission.entity.Category;
import umc9th_hackathon.daybreak.domain.mission.entity.Mission;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import umc9th_hackathon.daybreak.domain.mission.repository.CategoryRepository;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionRepository;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.MemberErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyMissionService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MissionSelectionRepository missionSelectionRepository;
    private final MissionRepository missionRepository;

    //OpenAI로부터 미션 3개 생성하는 클라이언트
    private final OpenAiMissionClient openAiMissionClient;

    public List<String> createWeeklyMissions(String email, WeeklyMissionCreateRequest req) {

        Member member = memberRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));

        Category category = categoryRepository.findByCategoryName(req.getCategory())
                .orElseThrow(() -> new GeneralException(MissionErrorCode.CATEGORY_NOT_FOUND));

        // member 당 MissionSelection은 1개 전제
        MissionSelection selection = missionSelectionRepository
                .findByMember_MemberId(member.getMemberId())
                .orElseGet(() -> MissionSelection.create(member, category, req.getGoal()));

        selection.updateSelection(category, req.getGoal());
        missionSelectionRepository.save(selection);

        // 기존 주간 미션 전부 삭제 후 재생성
        missionRepository.deleteAllByMissionSelection(selection);

        // AI로 미션 3개 생성 (실패 시 fallback)
        List<String> missionTexts = openAiMissionClient.generateWeeklyMissions(req.getCategory(), req.getGoal());

        List<Mission> missions = missionTexts.stream()
                .map(text -> Mission.builder()
                        .content(text)
                        .isSuccess(false)
                        .missionSelection(selection)
                        .build()
                ).toList();

        missionRepository.saveAll(missions);

        // 응답용으로 텍스트 리스트 반환
        return missionTexts;
    }
}
