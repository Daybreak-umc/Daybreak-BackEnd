package umc9th_hackathon.daybreak.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import umc9th_hackathon.daybreak.domain.member.dto.req.LoginRequest;
import umc9th_hackathon.daybreak.domain.member.dto.req.MemberSignupRequest;
import umc9th_hackathon.daybreak.domain.member.dto.res.LoginResponse;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;

@Tag(name = "Member API", description = "Member 관련 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원가입", description = "각각 최대 이름은 10자, 이메일, 비밀번호는 50자로 제한한다. " +
            "이메일, 이름, 비밀번호를 입력한다.")
    @PostMapping("/api/v1/auth/signup")
    ApiResponse<String> signup(@RequestBody @Valid MemberSignupRequest request);

    @Operation(summary = "로그인", description = "토큰을 발급받기 위한 기본 로그인이다." +
            "이메일과 비밀번호를 입력한다. " +
            "회원가입, 로그인을 제외한 모든 http 요청엔 토큰값을 헤더에 실어야 한다.")
    @PostMapping("/api/v1/auth/login")
    ApiResponse<LoginResponse> login(@RequestBody LoginRequest request);

    @Operation(summary = "로그아웃", description = "토큰을 블랙 리스트에 등록한다.")
    @PostMapping("/api/v1/auth/logout")
    ApiResponse<String> logout(HttpServletRequest request);

    @Operation(summary = "회원탈퇴", description = "해당 사용자를 db에서 삭제시킨다.")
    @DeleteMapping("/api/v1/auth/withdrawal")
    ApiResponse<String> withdraw(HttpServletRequest request);

}
