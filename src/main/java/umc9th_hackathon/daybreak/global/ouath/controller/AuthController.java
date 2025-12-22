package umc9th_hackathon.daybreak.global.ouath.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc9th_hackathon.daybreak.domain.member.dto.res.LoginResponse;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.service.MemberService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;
import umc9th_hackathon.daybreak.global.jwt.JwtTokenProvider;
import umc9th_hackathon.daybreak.global.ouath.dto.KakaoLoginDTO;
import umc9th_hackathon.daybreak.global.ouath.dto.KakaoUserInfoResDTO;
import umc9th_hackathon.daybreak.global.ouath.service.KakaoLoginService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final KakaoLoginService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @PostMapping("/kakao")
    @Override
    public ApiResponse<LoginResponse> kakaoLogin(@RequestBody KakaoLoginDTO request) {

        // 2. 카카오 사용자 정보
        KakaoUserInfoResDTO userInfo = kakaoService.getUserInfo(request.getAccessToken());

        // 3. DB 사용자 조회/생성
        Member member = memberService.findOrCreateKakaoUser(userInfo.getId().toString(),null);

        // 4. 서버 JWT 발급
        String jwtToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole());

        LoginResponse token = LoginResponse.builder()
                .jwtToken(jwtToken)
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode.REQUEST_OK,token);
    }
}
