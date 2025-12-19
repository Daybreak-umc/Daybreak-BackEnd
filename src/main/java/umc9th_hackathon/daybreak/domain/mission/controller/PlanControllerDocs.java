package umc9th_hackathon.daybreak.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import umc9th_hackathon.daybreak.domain.mission.dto.req.PlanReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.req.RandomGoalReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.RandomGoalResDTO;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;

@Tag(name = "Plan API", description = "Plan 관련 API")
public interface PlanControllerDocs {

    @Operation(summary = "Plan 설정", description = "사용자가 설정한 카테고리와 목표 토대로 Plan을 설정합니다.")
    ApiResponse<PlanResDTO.PlanDto> createPlan(PlanReqDTO request, Authentication authentication);

    @PostMapping("/api/v1/mission/random")
    ApiResponse<RandomGoalResDTO.RandomGoalDTO> createRandom(
            @RequestBody @Valid RandomGoalReqDTO request,
            Authentication authentication);
}
