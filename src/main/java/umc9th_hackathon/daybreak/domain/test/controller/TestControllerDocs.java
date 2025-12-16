package umc9th_hackathon.daybreak.domain.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "테스트 API", description = "테스트 관련 API")
public interface TestControllerDocs {

    @Operation(summary = "헬스체크", description = "컨트롤러 동작 확인용 API")
    String healthcheck();
}
