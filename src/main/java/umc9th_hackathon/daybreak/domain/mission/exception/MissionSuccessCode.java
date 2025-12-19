package umc9th_hackathon.daybreak.domain.mission.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc9th_hackathon.daybreak.global.apiPayload.code.BaseErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.BaseSuccessCode;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {
    SUCCESS_COMPLETE(HttpStatus.BAD_REQUEST,
            "MISSION_200",
            "미션이 성공적으로 처리되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;


}
