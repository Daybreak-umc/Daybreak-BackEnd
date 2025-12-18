package umc9th_hackathon.daybreak.domain.mission.controller;

import umc9th_hackathon.daybreak.domain.mission.dto.req.PlanReqDTO;
import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.global.apiPayload.ApiResponse;

public interface PlanControllerDocs {
    ApiResponse<PlanResDTO> createPlan(PlanReqDTO request);
}
