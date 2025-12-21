package umc9th_hackathon.daybreak.domain.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.res.MissionResponse;
import umc9th_hackathon.daybreak.domain.mission.service.MissionCommandService;
import umc9th_hackathon.daybreak.domain.mission.service.MissionQueryService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionSuccessCode;

@RestController
@RequiredArgsConstructor
public class MissionController implements MissionControllerDocs {

    private final MissionQueryService missionQueryService;
    private final MissionCommandService missionCommandService;

    @GetMapping("/api/v1/missions")
    @Override
    public ApiResponse<MissionResponse.MissionGroupListDto> getGroupMissions(Authentication authentication) {


        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,missionQueryService.getGroupMissions(authentication)
        );
    }

    @PatchMapping("/api/v1/missions/complete")
    @Override
    public ApiResponse<MissionResponse.MissionCompleteDto> completeMission(Authentication authentication,
                                                                           @RequestParam(value = "missionId") Long missionId
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.SUCCESS_COMPLETE,
                missionCommandService.patchMissionComplete(missionId, authentication)
        );
    }

    @DeleteMapping("/api/v1/missions/delete")
    @Override
    public ApiResponse<Void> deleteGoal(Authentication authentication) {

        missionCommandService.deleteGoal(authentication);

        return ApiResponse.onSuccess(MissionSuccessCode.GOAL_DELETED, null);
    }
}
