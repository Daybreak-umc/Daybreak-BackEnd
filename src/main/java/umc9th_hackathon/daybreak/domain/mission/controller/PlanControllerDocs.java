package umc9th_hackathon.daybreak.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import umc9th_hackathon.daybreak.domain.mission.dto.req.PlanReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.req.RandomGoalReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.RandomGoalResDTO;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;

@Tag(name = "계획 API", description = "AI 기반 목표 설정 및 계획 수립 관련 API")
public interface PlanControllerDocs {

    @Operation(
            summary = "AI 계획 생성",
            description = "사용자가 입력한 카테고리와 목표를 바탕으로 AI가 1년 단위의 단계별 계획을 생성합니다."
    )
    ApiResponse<PlanResDTO.PlanDto> createPlan(
            @Parameter(description = "계획 생성 요청 데이터 (카테고리, 목표)", required = true)
            @RequestBody @Valid PlanReqDTO request,

            @Parameter(hidden = true) // JWT 토큰에서 자동 추출되므로 Swagger에서 숨김
            Authentication authentication
    );

    @Operation(
            summary = "랜덤 목표 생성",
            description = "선택한 카테고리에 맞는 랜덤한 목표를 AI가 자동으로 생성합니다."
    )
    ApiResponse<RandomGoalResDTO.RandomGoalDTO> createRandom(
            @Parameter(description = "랜덤 목표 생성 요청 데이터 (카테고리)", required = true)
            @RequestBody @Valid RandomGoalReqDTO request,

            @Parameter(hidden = true) // JWT 토큰에서 자동 추출되므로 Swagger에서 숨김
            Authentication authentication
    );

    @Operation(
            summary = "미션 타임라인 조회",
            description = "생성된 계획의 단계별 타임라인을 조회합니다. (1주일, 1개월, 3개월, 6개월, 1년)"
    )
    ApiResponse<PlanResDTO.PlanDto> getMissionTimeline(
            @Parameter(hidden = true) // JWT 토큰에서 자동 추출되므로 Swagger에서 숨김
            Authentication authentication,

            @Parameter(description = "조회할 계획 ID (Mission Selection ID)", required = true, example = "1")
            @RequestParam(value = "planId") Long missionSelectionId
    );
}