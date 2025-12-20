package umc9th_hackathon.daybreak.domain.mission.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc9th_hackathon.daybreak.global.apiPayload.code.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER400_1",
            "등록되지 않은 사용자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
