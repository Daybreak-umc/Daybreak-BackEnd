package umc9th_hackathon.daybreak.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.member.converter.MemberConverter;
import umc9th_hackathon.daybreak.domain.member.dto.req.LoginRequest;
import umc9th_hackathon.daybreak.domain.member.dto.req.MemberSignupRequest;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.member.repository.MemberRepository;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.MemberErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;
import umc9th_hackathon.daybreak.global.enums.Role;
import umc9th_hackathon.daybreak.global.jwt.JwtTokenProvider;
import umc9th_hackathon.daybreak.global.jwt.TokenBlacklist;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklist tokenBlacklist;

    public void signup(MemberSignupRequest request) {
        //1. 이메일 중복 체크
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new GeneralException(MemberErrorCode.DUPLICATE_EMAIL);
        }
        //2. 비밀번호 bcryt 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // 3. 컨버터를 통해 엔티티 생성
        Member member = MemberConverter.toMember(request, encodedPassword);
        // 4. 저장
        memberRepository.save(member);
    }

    // 토큰 기반 로그인 (소셜 로그인 시 사용하지 않음)
    public String login(LoginRequest request) {
        // 1. 이메일 확인
        Member member = (Member) memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 2. 비밀번호 일치 확인 (Security의 역할)
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new GeneralException(GeneralErrorCode.UNAUTHORIZED);
        }

        // 3. 로그인 성공 시 토큰 생성 및 반환
        return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
    }

    @Transactional
    public void logout(String token) {
        tokenBlacklist.add(token);
    }

    public void withdraw(String token) {
        // 1. 토큰에서 사용자 이메일 직접 추출
        String email = jwtTokenProvider.getUserEmail(token);

        // 2. DB에서 실제 사용자 존재 여부 및 탈퇴 여부 검증
        Member member = memberRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 3. 탈퇴 처리 (Soft Delete)
        memberRepository.delete(member);

        // 4. 사용했던 토큰을 블랙리스트에 등록하여 즉시 차단
        tokenBlacklist.add(token);
    }

    public Member getCurrentMember(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        // ✅ JWT 필터나 세션이 바로 Member를 넣어준 경우
        if (principal instanceof Member member) {
            return member;
        }

        // ✅ OAuth2 (카카오) 로그인인 경우
        if (principal instanceof OAuth2User oAuth2User) {
            String kakaoId = oAuth2User.getAttribute("id").toString();
            return memberRepository.findByKakaoId(kakaoId)
                    .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
        }

        // ✅ 그 밖의 경우 (기본적으로 username = email로 보는 경우)
        String email = authentication.getName();
        return memberRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    // 카카오 로그인 시 유저 정보가 존재하지 않을 때, 카카오 데이터 기반으로 DB에 회원 정보 저장 (비밀번호의 경우 사용자의 kakaoId를 암호화해서 저장)
    @Transactional
    public Member findOrCreateKakaoUser(String kakaoId, String name) {
        return memberRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> {
                    Member member = Member.builder()
                            .kakaoId(kakaoId)
                            .name(name != null ? name : "KakaoUser")
                            .email("kakao_" + kakaoId.substring(0, 8) + "@example.com")
                            .password(passwordEncoder.encode(kakaoId))
                            .role(Role.ROLE_USER)
                            .build();
                    return memberRepository.save(member);
                });
    }

}
