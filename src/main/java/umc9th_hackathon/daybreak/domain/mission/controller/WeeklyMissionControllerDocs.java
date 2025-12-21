package umc9th_hackathon.daybreak.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.WeeklyMissionResDTO;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;

@Tag(name = "주간 미션 API", description = "AI가 생성하는 주간 미션 관련 API")
public interface WeeklyMissionControllerDocs {

    @Operation(
            summary = "주간 미션 생성", 
            description = "특정 목표에 대한 주간 미션 3개를 AI가 생성합니다. 목표 생성일에 따라 난이도가 자동으로 조정됩니다. (1주일: 초급, 1개월: 초중급, 3개월: 중급, 6개월: 중고급, 6개월+: 고급)"
    )
    ApiResponse<WeeklyMissionResDTO.WeeklyMissionDTO> createWeeklyMissions(
            @Parameter(description = "주간 미션 생성 요청 정보", required = true)
            @RequestBody @Valid WeeklyMissionReqDTO request,
            Authentication authentication
    );
}