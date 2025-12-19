package umc9th_hackathon.daybreak.domain.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.req.PlanReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.service.PlanQueryService;
import umc9th_hackathon.daybreak.domain.mission.service.PlanService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mission")
public class PlanController implements PlanControllerDocs {

    private final PlanService planService;
    private final PlanQueryService planQueryService;


    @Override
    @PostMapping("/plan")
    public ApiResponse<PlanResDTO.PlanDto> createPlan(
            @RequestBody @Valid PlanReqDTO request,
            Authentication authentication) {

        PlanResDTO.PlanDto plan = planService.createPlan(request, authentication);

        return ApiResponse.onSuccess(GeneralSuccessCode.REQUEST_OK, plan);
    }


    @GetMapping("/api/v1/missions/timeline")
    public ApiResponse<PlanResDTO.PlanDto> getMissionTimeline(
            Authentication authentication,
            @RequestParam(value="planId" , required = false) Long missionSelectionId)
    {


        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,
                planQueryService.getPlan(authentication, missionSelectionId)
        );
    }

}