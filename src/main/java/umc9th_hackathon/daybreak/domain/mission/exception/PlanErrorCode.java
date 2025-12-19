package umc9th_hackathon.daybreak.domain.mission.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc9th_hackathon.daybreak.global.apiPayload.code.BaseErrorCode;
@Getter
@AllArgsConstructor
public enum PlanErrorCode  implements BaseErrorCode {
    NO_PLANID(HttpStatus.BAD_REQUEST,
            "PLAN400_1",
            "PlanId는 필수 입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
