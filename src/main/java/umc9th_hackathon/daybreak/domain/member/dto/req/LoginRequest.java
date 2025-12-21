package umc9th_hackathon.daybreak.domain.member.dto.req;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;

    private String password;
}
