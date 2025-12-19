package umc9th_hackathon.daybreak.domain.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionCreateRequest;
import umc9th_hackathon.daybreak.domain.mission.dto.res.WeeklyMissionCreateResponse;
import umc9th_hackathon.daybreak.domain.mission.service.WeeklyMissionService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class WeeklyMissionController {

    private final WeeklyMissionService weeklyMissionService;

    @PostMapping("/weekly")
    public ApiResponse<WeeklyMissionCreateResponse> createWeeklyMissions(
            @RequestBody @Valid WeeklyMissionCreateRequest req
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        String email = auth.getName();
        WeeklyMissionCreateResponse res =
                weeklyMissionService.createWeeklyMissionsByEmail(email, req);

        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CREATED, res);
    }
}
