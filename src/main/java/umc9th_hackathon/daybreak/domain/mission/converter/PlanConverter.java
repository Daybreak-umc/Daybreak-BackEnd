package umc9th_hackathon.daybreak.domain.mission.converter;

import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResDTO;
import umc9th_hackathon.daybreak.domain.mission.entity.Plan;

public class PlanConverter {
    public static PlanResDTO.PlanDto toPlanDto(Plan plan) {
        if (plan == null) return null;

        return new PlanResDTO.PlanDto(
                plan.getWeekPlan(),
                plan.getMonth1Plan(),
                plan.getMonth3Plan(),
                plan.getMonth6Plan(),
                plan.getYearPlan(),
                plan.getCreateTime()
        );
    }
}
