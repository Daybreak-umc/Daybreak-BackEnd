package umc9th_hackathon.daybreak.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
    ),
    CATEGORY_GOAL_MISMATCH(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS,"MISSION_404_3","카테고리와 목표 사이에 관련성이 없습니다."),

    DUPLICATE_GOAL(HttpStatus.CONFLICT, "MISSION409_1", "이미 존재하는 목표입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "존재하지 않는 카테고리입니다."),
    MAX_GOALS_EXCEEDED(HttpStatus.BAD_REQUEST, "MISSION400_1", "목표는 최대 4개까지만 생성할 수 있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

