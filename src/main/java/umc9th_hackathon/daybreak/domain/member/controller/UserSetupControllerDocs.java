package umc9th_hackathon.daybreak.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import umc9th_hackathon.daybreak.domain.member.dto.req.UserSetupRequest;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;

@Tag(name = "MemberSetUpAPI", description = "Member 초기 설정API")
public interface UserSetupControllerDocs {

    @Operation(summary = "사용자 초기 설정", description = "사용자가 회원가입을 완료한 직후 앱을 본격적으로 사용 시작 전 입력 사항이다. " +
            "category엔 건강, 학업, 커리어..... 중 하나를 입력하고 " +
            "goal엔 '나는 ~~~한 사람이 되어있을 거에요'에서 ~~~를 입력하면 된다.")
    @PostMapping("/setup")
    ApiResponse<?> setup(@RequestBody @Valid UserSetupRequest request);


}
