package umc9th_hackathon.daybreak.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.MissionResponse;
import umc9th_hackathon.daybreak.domain.mission.dto.res.WeeklyMissionResDTO;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;

@Tag(name = "미션 API", description = "주간 미션 조회, 완료, 목표 삭제 관련 API")
public interface MissionControllerDocs {

    @Operation(
            summary = "주간 미션 생성",
            description = "특정 목표에 대한 주간 미션 3개를 AI가 생성합니다. 목표 생성일에 따라 난이도가 자동으로 조정됩니다. (1주일: 초급, 1개월: 초중급, 3개월: 중급, 6개월: 중고급, 6개월+: 고급)"
    )
    ApiResponse<WeeklyMissionResDTO.WeeklyMissionDTO> createWeeklyMissions(
            @Parameter(description = "주간 미션 생성 요청 정보", required = true)
            @RequestBody @Valid WeeklyMissionReqDTO request,
            Authentication authentication
    );

    @Operation(
            summary = "미션 목록 조회",
            description = "사용자의 카테고리별 미션 목록을 조회합니다. 각 카테고리마다 목표와 관련 미션들을 반환합니다."
    )
    ApiResponse<MissionResponse.MissionGroupListDto> getGroupMissions(
            Authentication authentication
    );

    @Operation(
            summary = "미션 완료 처리",
            description = "특정 미션을 완료 상태로 변경합니다. 미션 ID를 통해 해당 미션의 완료 처리를 수행합니다."
    )
    ApiResponse<MissionResponse.MissionCompleteDto> completeMission(
            Authentication authentication, @RequestParam(value = "missionId") Long missionId
    );

    @Operation(
            summary = "목표 삭제",
            description = "현재 사용자의 모든 목표와 관련된 미션들을 삭제합니다."
    )
    ApiResponse<Void> deleteGoal(Authentication authentication);
}
