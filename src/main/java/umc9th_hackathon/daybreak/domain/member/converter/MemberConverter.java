package umc9th_hackathon.daybreak.domain.member.converter;

import umc9th_hackathon.daybreak.domain.member.dto.req.MemberSignupRequest;
import umc9th_hackathon.daybreak.domain.member.dto.res.LoginResponse;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.global.auth.enums.Role;

public class MemberConverter {

    public static Member toMember(MemberSignupRequest request, String encodedPassword) {
        return Member.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .role(Role.ROLE_USER)// 기본 권한
                .build();
    }

    public static LoginResponse toLoginResult(String token) {
        return LoginResponse.builder()
                .jwtToken(token)
                .build();
    }

}
