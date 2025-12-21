package umc9th_hackathon.daybreak.domain.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.WeeklyMissionResDTO;
import umc9th_hackathon.daybreak.domain.mission.service.WeeklyMissionService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mission")
public class WeeklyMissionController implements WeeklyMissionControllerDocs {

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
}