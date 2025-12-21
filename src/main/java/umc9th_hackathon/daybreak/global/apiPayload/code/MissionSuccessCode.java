package umc9th_hackathon.daybreak.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    GOAL_DELETED(HttpStatus.OK,
            "COMMON_200", "목표가 성공적으로 삭제되었습니다."),
    SUCCESS_COMPLETE(HttpStatus.BAD_REQUEST,
            "MISSION_200",
            "미션이 성공적으로 처리되었습니다."),

    MISSION_CREATED(HttpStatus.OK, "COMMON_200", "미션이 성공적으로 제작되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

