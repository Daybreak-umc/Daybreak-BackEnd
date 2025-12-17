package umc9th_hackathon.daybreak.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import umc9th_hackathon.daybreak.global.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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




}
