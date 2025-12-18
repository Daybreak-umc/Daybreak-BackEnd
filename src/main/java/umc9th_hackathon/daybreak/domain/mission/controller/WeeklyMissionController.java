package umc9th_hackathon.daybreak.domain.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.mission.dto.req.WeeklyMissionCreateRequest;
import umc9th_hackathon.daybreak.domain.mission.service.WeeklyMissionService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.MissionSuccessCode;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class WeeklyMissionController {

    private final WeeklyMissionService weeklyMissionService;

    @PostMapping
    public ApiResponse<?> createWeeklyMissions(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid WeeklyMissionCreateRequest req
    ) {
        String email = userDetails.getUsername(); // JWT subject=email
        List<String> missions = weeklyMissionService.createWeeklyMissions(email, req);

        return ApiResponse.onSuccess(
                MissionSuccessCode.MISSION_CREATED,
                missions
        );
    }
}
