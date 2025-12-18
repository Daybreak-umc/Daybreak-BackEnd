package umc9th_hackathon.daybreak.domain.member.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginResponse {
    @JsonProperty("jwt_token")
    private String jwtToken;
}
