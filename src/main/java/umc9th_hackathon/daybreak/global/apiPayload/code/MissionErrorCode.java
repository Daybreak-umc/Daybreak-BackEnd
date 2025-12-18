package umc9th_hackathon.daybreak.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "존재하지 않는 카테고리입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
