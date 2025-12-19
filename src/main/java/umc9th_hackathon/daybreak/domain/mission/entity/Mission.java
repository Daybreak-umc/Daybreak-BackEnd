package umc9th_hackathon.daybreak.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc9th_hackathon.daybreak.global.entity.BaseEntity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "mission")
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;


    @Column(name = "content", length = 50, nullable = false)
    private String content;

    @Column(name = "is_success", nullable = false)
    private boolean isSuccess;


    // 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selection_id")
    private MissionSelection missionSelection;

    public static Mission create(MissionSelection selection, String content) {
        return Mission.builder()
                .missionSelection(selection)
                .content(content)
                .isSuccess(false) //기본값
                .build();
    }

    public void complete() {
        this.isSuccess = true;
    }
}