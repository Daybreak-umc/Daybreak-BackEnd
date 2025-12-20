package umc9th_hackathon.daybreak.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import umc9th_hackathon.daybreak.domain.member.dto.req.UserSetupRequest;
import umc9th_hackathon.daybreak.domain.member.service.UserSetupService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserSetupController implements UserSetupControllerDocs{

    private final UserSetupService userSetupService;

    @PostMapping("/setup")
    public ApiResponse<?> setup(@RequestBody @Valid UserSetupRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String email = principal.getUsername(); // JwtTokenProvider에서 subject(email)로 넣어둔 값

        userSetupService.setupByEmail(email, request);

        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,
                "카테고리와 목표가 설정되었습니다."
        );
    }
}
