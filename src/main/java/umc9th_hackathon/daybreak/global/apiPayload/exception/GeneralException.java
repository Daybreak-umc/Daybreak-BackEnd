package umc9th_hackathon.daybreak.global.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import umc9th_hackathon.daybreak.global.apiPayload.code.BaseErrorCode;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseErrorCode code;
}
