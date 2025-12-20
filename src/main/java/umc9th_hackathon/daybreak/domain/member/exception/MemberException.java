package umc9th_hackathon.daybreak.domain.member.exception;

import umc9th_hackathon.daybreak.global.apiPayload.code.BaseErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.exception.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(BaseErrorCode code) {
        super(code);
    }
}
