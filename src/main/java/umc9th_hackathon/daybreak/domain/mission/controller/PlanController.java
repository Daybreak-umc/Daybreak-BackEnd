package umc9th_hackathon.daybreak.domain.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.req.PlanReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.req.PlanReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.req.RandomGoalReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.RandomGoalResDTO;
import umc9th_hackathon.daybreak.domain.mission.service.PlanService;
import umc9th_hackathon.daybreak.domain.mission.service.PlanQueryService;
import umc9th_hackathon.daybreak.domain.mission.service.PlanService;
import umc9th_hackathon.daybreak.domain.mission.service.RandomGoalService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mission")
public class PlanController implements PlanControllerDocs {

    private final PlanService planService;
    private final PlanQueryService planQueryService;
    private final RandomGoalService randomGoalService;

    @Override
    @PostMapping("/plan")
    public ApiResponse<PlanResDTO.PlanDto> createPlan(
            @RequestBody @Valid PlanReqDTO request,
            Authentication authentication) {

        PlanResDTO.PlanDto plan = planService.createPlan(request, authentication);

        return ApiResponse.onSuccess(GeneralSuccessCode.REQUEST_OK, plan);
    }

    @PostMapping("/random")
    @Override
    public ApiResponse<RandomGoalResDTO.RandomGoalDTO> createRandom(
            @RequestBody @Valid RandomGoalReqDTO request,
            Authentication authentication) {

        RandomGoalResDTO.RandomGoalDTO randomgoal = randomGoalService.createRandomGoal(request, authentication);

        return ApiResponse.onSuccess(GeneralSuccessCode.REQUEST_OK, randomgoal);
    }

    @GetMapping("/timeline")
    public ApiResponse<PlanResDTO.PlanDto> getMissionTimeline(
            Authentication authentication,
            @RequestParam(value="planId") Long missionSelectionId)
     {
         return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,
                planQueryService.getPlan(authentication, missionSelectionId)
        );
    }

}