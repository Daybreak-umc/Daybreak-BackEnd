package umc9th_hackathon.daybreak.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionCreateRequest;
import umc9th_hackathon.daybreak.domain.mission.dto.res.WeeklyMissionCreateResponse;
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

import static umc9th_hackathon.daybreak.global.apiPayload.code.MissionErrorCode.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class WeeklyMissionService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MissionSelectionRepository missionSelectionRepository;
    private final MissionRepository missionRepository;
    private final OpenAiMissionClient openAiMissionClient;

    @Transactional
    public WeeklyMissionCreateResponse createWeeklyMissionsByEmail(String email, WeeklyMissionCreateRequest req) {

        Category category = categoryRepository.findByCategoryName(req.category())
                .orElseThrow(() -> new GeneralException(MissionErrorCode.CATEGORY_NOT_FOUND));

        Member member = (Member) memberRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));



        // 멤버당 MissionSelection 1개 유지하는 구조면 이게 제일 안전함
        MissionSelection selection = missionSelectionRepository.findByMember_MemberId(member.getMemberId())
                .orElseGet(() -> missionSelectionRepository.save(
                        MissionSelection.create(member, category, req.goal())
                ));

        // 기존 selection이면 최신 목표/카테고리로 갱신
        selection.updateSelection(category, req.goal());

        // OpenAI로 미션 3개 생성
        List<String> missionsText = openAiMissionClient.generateWeeklyMissions(req.category(), req.goal());

        // 중복 방지: 기존 미션 삭제 후 새로 저장
        missionRepository.deleteByMissionSelection(selection);

        List<Mission> missions = missionsText.stream()
                .map(text -> Mission.create(selection, text))
                .toList();

        missionRepository.saveAll(missions);

        return new WeeklyMissionCreateResponse(missionsText);
    }
}
