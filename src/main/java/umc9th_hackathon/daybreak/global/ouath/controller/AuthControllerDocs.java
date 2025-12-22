package umc9th_hackathon.daybreak.global.ouath.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import umc9th_hackathon.daybreak.domain.member.dto.res.LoginResponse;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.ouath.dto.KakaoLoginDTO;

@Tag(name = "카카오 Ouath 로그인 API", description = "카카오 로그인 관련 API")
public interface AuthControllerDocs {

    @Operation(
            summary = "카카오 Oauth 로그인",
            description = "클라이언트에서 카카오 로그인을 통해 받은 accesstoken과 refreshtoken 값을 입력하여 서버에서 사용할 jwt 토큰을 제공받습니다."
    )
    @PostMapping("/kakao")
    ApiResponse<LoginResponse> kakaoLogin(@RequestBody KakaoLoginDTO request);
}
