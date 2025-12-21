package umc9th_hackathon.daybreak.global.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import umc9th_hackathon.daybreak.domain.member.service.MemberService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String kakaoId = oAuth2User.getAttribute("id").toString();
        String nickname = oAuth2User.getAttribute("nickname").toString();

        // DB에 없으면 자동 생성
        memberService.findOrCreateKakaoUser(kakaoId, nickname);

        log.info("OAuth2 사용자 생성/로그인 완료: {}", kakaoId);
        response.sendRedirect("/swagger-ui/index.html");
    }
}
