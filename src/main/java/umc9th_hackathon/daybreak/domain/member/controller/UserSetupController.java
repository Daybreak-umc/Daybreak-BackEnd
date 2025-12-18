package umc9th_hackathon.daybreak.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import umc9th_hackathon.daybreak.domain.member.dto.req.UserSetupRequest;
import umc9th_hackathon.daybreak.domain.member.service.UserSetupService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserSetupController {

    private final UserSetupService userSetupService;

    @PostMapping("/setup")
    public ApiResponse<?> setup(
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @RequestBody @Valid UserSetupRequest request
    ) {
        userSetupService.setup(memberId, request);

        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,
                "카테고리와 목표가 설정되었습니다."
        );
    }
}
