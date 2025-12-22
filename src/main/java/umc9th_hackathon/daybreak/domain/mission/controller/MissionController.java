package umc9th_hackathon.daybreak.domain.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.MissionResponse;
import umc9th_hackathon.daybreak.domain.mission.dto.res.WeeklyMissionResDTO;
import umc9th_hackathon.daybreak.domain.mission.service.MissionCommandService;
import umc9th_hackathon.daybreak.domain.mission.service.MissionQueryService;
import umc9th_hackathon.daybreak.domain.mission.service.WeeklyMissionService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mission")
public class MissionController implements MissionControllerDocs {

    private final MissionQueryService missionQueryService;
    private final MissionCommandService missionCommandService;

    private final WeeklyMissionService weeklyMissionService;

    @Override
    @PostMapping("/week")
    public ApiResponse<WeeklyMissionResDTO.WeeklyMissionDTO> createWeeklyMissions(
            @RequestBody @Valid WeeklyMissionReqDTO request,
            Authentication authentication) {

        WeeklyMissionResDTO.WeeklyMissionDTO weeklyMissions =
                weeklyMissionService.createWeeklyMissions(request, authentication);

        return ApiResponse.onSuccess(GeneralSuccessCode.REQUEST_OK, weeklyMissions);
    }

    @GetMapping
    @Override
    public ApiResponse<MissionResponse.MissionGroupListDto> getGroupMissions(Authentication authentication) {


        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,missionQueryService.getGroupMissions(authentication)
        );
    }

    @PatchMapping("/complete")
    @Override
    public ApiResponse<MissionResponse.MissionCompleteDto> completeMission(Authentication authentication,
                                                                           @RequestParam(value = "missionId") Long missionId
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.SUCCESS_COMPLETE,
                missionCommandService.patchMissionComplete(missionId, authentication)
        );
    }

    @DeleteMapping
    @Override
    public ApiResponse<Void> deleteGoal(Authentication authentication) {

        missionCommandService.deleteGoal(authentication);

        return ApiResponse.onSuccess(MissionSuccessCode.GOAL_DELETED, null);
    }
}
