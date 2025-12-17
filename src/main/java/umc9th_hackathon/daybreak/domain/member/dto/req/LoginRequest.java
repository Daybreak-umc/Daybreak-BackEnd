package umc9th_hackathon.daybreak.domain.member.dto.req;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class LoginRequest {

    @Length(max = 50)
    private String email;

    @Length(max = 100)
    private String password;
}
