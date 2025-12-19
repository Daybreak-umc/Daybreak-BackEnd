package umc9th_hackathon.daybreak.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "mission_selection")
public class MissionSelection extends BaseEntity {

    @Id
    @Column(name = "mission_selection_id")
    private Long missionSelectionId;

    @Column(name = "objective", length = 50, nullable = false)
    private String objective;

    // 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @OneToMany(mappedBy = "missionSelection", cascade = CascadeType.ALL)
    private List<Mission> memberMissions = new ArrayList<>();

    @OneToOne(mappedBy = "missionSelection", cascade = CascadeType.ALL ,optional = false)
    private Plan plan;
}