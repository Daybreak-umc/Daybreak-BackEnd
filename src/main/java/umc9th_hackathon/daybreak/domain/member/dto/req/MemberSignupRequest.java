package umc9th_hackathon.daybreak.domain.member.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignupRequest {

    @Email
    @NotBlank
    @Length(max = 50)
    private String email;

    @NotBlank
    @Length(max = 10)
    private String name;

    @NotBlank
    @Length(max = 100)
    private String password;
}
