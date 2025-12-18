package umc9th_hackathon.daybreak.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc9th_hackathon.daybreak.global.entity.BaseEntity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "plan")
public class Plan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "week_plan", length = 50, nullable = false)
    private String weekPlan;

    @Column(name = "month1_plan", length = 50, nullable = false)
    private String month1Plan;

    @Column(name = "month3_plan", length = 50, nullable = false)
    private String month3Plan;

    @Column(name = "month6_plan", length = 50, nullable = false)
    private String month6Plan;

    @Column(name = "year_plan", length = 50, nullable = false)
    private String yearPlan;

    // 연관 관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_selection_id",nullable = false,unique = true)
    private MissionSelection missionSelection;
}