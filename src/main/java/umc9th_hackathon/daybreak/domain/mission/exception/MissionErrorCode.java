package umc9th_hackathon.daybreak.domain.mission.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc9th_hackathon.daybreak.global.apiPayload.code.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {
    Need_MISSIONID(HttpStatus.BAD_REQUEST,
            "MISSION_400_1",
            "미션ID가 필요합니다."),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND,
                      "MISSION_400_2",
                        "미션을 찾을  수 없습니다."),
    MISSION_SELECTION_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "MISSION_404_2",
            "삭제할 목표가 없습니다."
    );
    private final HttpStatus status;
    private final String code;
    private final String message;
}
