package umc9th_hackathon.daybreak.domain.mission.converter;

import umc9th_hackathon.daybreak.domain.mission.dto.res.PlanResponse;
import umc9th_hackathon.daybreak.domain.mission.entity.Plan;

public class PlanConverter {
    public static PlanResponse.PlanDto toPlanDto(Plan plan) {
        if (plan == null) return null;

        return new PlanResponse.PlanDto(
                plan.getWeekPlan(),
                plan.getMonth1Plan(),
                plan.getMonth3Plan(),
                plan.getMonth6Plan(),
                plan.getYearPlan(),
                plan.getCreateTime()
        );
    }
}
