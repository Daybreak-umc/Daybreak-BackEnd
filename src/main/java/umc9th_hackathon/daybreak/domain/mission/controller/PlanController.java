package umc9th_hackathon.daybreak.domain.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResponse;
import umc9th_hackathon.daybreak.domain.mission.service.PlanQueryService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

@RestController
@RequiredArgsConstructor
public class PlanController {
    private final PlanQueryService planQueryService;

    @GetMapping("/api/v1/missions/timeline")
    public ApiResponse<PlanResponse.PlanDto> getMissionTimeline(
            Authentication authentication,
            @RequestParam(value="planId" , required = false) Long missionSelectionId)
     {


         return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,
                planQueryService.getPlan(authentication, missionSelectionId)
         );
    }
}
