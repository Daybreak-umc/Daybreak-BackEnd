package umc9th_hackathon.daybreak.domain.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.res.MissionResponse;
import umc9th_hackathon.daybreak.domain.mission.exception.MissionSuccessCode;
import umc9th_hackathon.daybreak.domain.mission.service.MissionCommandService;
import umc9th_hackathon.daybreak.domain.mission.service.MissionQueryService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionQueryService missionQueryService;
    private final MissionCommandService missionCommandService;

    @GetMapping("/api/v1/missions")
    public ApiResponse<MissionResponse.MissionGroupListDto> getGroupMissions(Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,missionQueryService.getGroupMissions(email)
        );
    }

    @PatchMapping("/api/v1/missions/complete")
    public ApiResponse<MissionResponse.MissionCompleteDto> completeMission(Authentication authentication,
           @RequestParam(value="missionId") Long missionId
    ) {
        String email = authentication.getName();

        return ApiResponse.onSuccess(
                MissionSuccessCode.SUCCESS_COMPLETE, missionCommandService.patchMissionComplete(missionId,email)
        );
    }

    @DeleteMapping("/api/v1/missions/delete")
    public ApiResponse<Void> deleteGoal() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        missionCommandService.deleteGoalByEmail(email);

        return ApiResponse.onSuccess(MissionSuccessCode.GOAL_DELETED, null);
    }
}
