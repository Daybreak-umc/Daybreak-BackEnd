package umc9th_hackathon.daybreak.domain.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSetupRequest {

    @NotBlank(message = "category는 필수입니다.")
    private String category;

    @NotBlank(message = "goal은 필수입니다.")
    private String goal;
}
