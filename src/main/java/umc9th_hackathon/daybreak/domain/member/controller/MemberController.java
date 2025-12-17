package umc9th_hackathon.daybreak.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc9th_hackathon.daybreak.domain.member.converter.MemberConverter;
import umc9th_hackathon.daybreak.domain.member.dto.req.LoginRequest;
import umc9th_hackathon.daybreak.domain.member.dto.req.MemberSignupRequest;
import umc9th_hackathon.daybreak.domain.member.dto.res.LoginResponse;
import umc9th_hackathon.daybreak.domain.member.service.MemberService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

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

    @PostMapping("/api/v1/auth/logout")
    public ApiResponse<String> logout(HttpServletRequest request) {
        // 1. 헤더에서 토큰 추출
        String token = getToken(request);

        // 2. 로그아웃
        memberService.logout(token);

        //3. 응답값 반환
        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,
                "성공적으로 로그아웃되었습니다."

        );
    }

    @DeleteMapping("/api/v1/auth/withdrawal")
    public ApiResponse<String> withdraw(HttpServletRequest request) {

        String token = getToken(request);

        memberService.withdraw(token);

        return ApiResponse.onSuccess(
                GeneralSuccessCode.REQUEST_OK,
                "회원 탈퇴가 완료되었습니다."
        );
    }

    //토큰 추출 메서드
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return token;
        }
        if (token == null) {
            throw new GeneralException(GeneralErrorCode.INTERNAL_SERVER_ERROR);
        }
        return "";
    }
}










