package umc9th_hackathon.daybreak.domain.mission.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc9th_hackathon.daybreak.domain.mission.dto.req.PlanReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.service.UpstageLlmService;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;
import umc9th_hackathon.daybreak.global.apiPayload.code.GeneralSuccessCode;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/mission")
public class PlanController implements PlanControllerDocs {

    private final UpstageLlmService llmService;

    public PlanController(UpstageLlmService llmService) {
        this.llmService = llmService;
    }

    @Override
    @PostMapping("/plan")
    public ApiResponse<PlanResDTO> createPlan(@RequestBody @Valid PlanReqDTO request) {
        PlanResDTO plan = llmService.getCleanPlan(request.category(), request.goal());
        return ApiResponse.onSuccess(GeneralSuccessCode.REQUEST_OK, plan);
    }
}