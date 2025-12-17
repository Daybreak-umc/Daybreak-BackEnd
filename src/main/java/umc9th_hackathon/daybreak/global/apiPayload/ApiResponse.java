package umc9th_hackathon.daybreak.global.apiPayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import umc9th_hackathon.daybreak.global.apiPayload.code.BaseErrorCode;
import umc9th_hackathon.daybreak.global.apiPayload.code.BaseSuccessCode;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("success")
    private final Boolean success;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("data")
    private T data;

    // 성공한 경우 (result 포함)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T data) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), data);
    }

    // 실패한 경우 (result 포함)
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T data) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), data);
    }
}
