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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_selection_id")
    private Long missionSelectionId;

    @Column(name = "objective", length = 50, nullable = false)
    private String objective;

    // 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder.Default
    @OneToMany(mappedBy = "missionSelection", cascade = CascadeType.ALL)
    private List<Mission> memberMissions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "missionSelection", cascade = CascadeType.ALL)
    private List<Plan> plans = new ArrayList<>();

    //변경 메서드
    public void updateSelection(Category category, String objective) {
        this.category = category;
        this.objective = objective;
    }

    public static MissionSelection create(Member member, Category category, String objective) {
        return MissionSelection.builder()
                .member(member)
                .category(category)
                .objective(objective)
                .build();
    }
}