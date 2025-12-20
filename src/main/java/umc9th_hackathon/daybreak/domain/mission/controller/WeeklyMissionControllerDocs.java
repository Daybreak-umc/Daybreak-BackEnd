package umc9th_hackathon.daybreak.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionCreateRequest;
import umc9th_hackathon.daybreak.domain.mission.dto.res.WeeklyMissionCreateResponse;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;

@Tag(name = "1주일 미션 생성 API", description = "1주일치 미션 생성 api")
public interface WeeklyMissionControllerDocs {

    @Operation(
            summary = "미션 생성",
            description = "사용자가 입력한 카테고리와 목표를 바탕으로 AI가 1주일치 미션을 생성합니다." +
                    "category엔 건강, 학업, 커리어..... 중 하나를 입력하고 " +
                    "goal엔 '나는 ~~~한 사람이 되어있을 거에요'에서 ~~~를 입력하면 된다."
    )
    @PostMapping("/weekly")
    ApiResponse<WeeklyMissionCreateResponse> createWeeklyMissions(
            @RequestBody @Valid WeeklyMissionCreateRequest req
    );

}
