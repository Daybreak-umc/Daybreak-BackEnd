package umc9th_hackathon.daybreak.global.ouath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class KakaoLoginDTO {

    public String accessToken;
    public String refreshToken;
}