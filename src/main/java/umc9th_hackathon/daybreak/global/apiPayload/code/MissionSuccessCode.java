package umc9th_hackathon.daybreak.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    MISSION_CREATED(HttpStatus.OK, "COMMON_200", "미션이 성공적으로 제작되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

