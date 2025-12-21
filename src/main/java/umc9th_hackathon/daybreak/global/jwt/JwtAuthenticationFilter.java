package umc9th_hackathon.daybreak.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
import umc9th_hackathon.daybreak.global.enums.Role;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklist tokenBlacklist;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, TokenBlacklist tokenBlacklist, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlacklist = tokenBlacklist;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // 클래스 내부에 resolveToken 메서드 넣기!
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    //토큰 검증 메서드 + 로그아웃 되서 블랙리스트 된 토큰까지 확인
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = resolveToken(request);

        if (jwt != null && jwtTokenProvider.validateToken(jwt)) {

            if (tokenBlacklist.isBlacklisted(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }
            Authentication auth = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);


    // 2. OAuth2 세션 확인 (가장 중요!)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof OAuth2User oAuth2User) {
            String kakaoId = ((OAuth2User) auth.getPrincipal()).getAttribute("id").toString();

            // kakaoId로 DB 사용자 조회/생성
            Member member = memberRepository.findByKakaoId(kakaoId)
                    .orElseGet(() -> {

                        Member newMember = Member.builder()
                                .kakaoId(kakaoId)
                                .name(oAuth2User.getAttribute("nickname") != null ?
                                        oAuth2User.getAttribute("nickname").toString() : "KakaoUser")
                                .email("kakao_" + kakaoId.substring(0, 8) + "@example.com")
                                .password(passwordEncoder.encode("OAUTH2_USER"))
                                .role(Role.ROLE_USER)
                                .build();
                        return memberRepository.save(newMember);
                    });

            List<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + member.getRole().name())  // "ROLE_USER"
            );

            // SecurityContext에 Member 정보 설정
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    member, null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}