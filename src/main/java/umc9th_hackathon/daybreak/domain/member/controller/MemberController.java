package umc9th_hackathon.daybreak.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc9th_hackathon.daybreak.domain.member.converter.MemberConverter;
import umc9th_hackathon.daybreak.domain.member.dto.req.LoginRequest;
import umc9th_hackathon.daybreak.domain.member.dto.req.MemberSignupRequest;
import umc9th_hackathon.daybreak.domain.member.dto.res.LoginResponse;
import umc9th_hackathon.daybreak.domain.member.service.MemberService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/auth/signup")
    public ApiResponse<String> signup(@RequestBody @Valid MemberSignupRequest request) {

        memberService.signup(request);

        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK, "회원가입이 완료되었습니다!"
        );

    }

    @PostMapping("/api/v1/auth/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = memberService.login(request);

        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,
                MemberConverter.toLoginResult(token)
        );

    }









}
