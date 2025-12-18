    package umc9th_hackathon.daybreak.domain.mission.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import umc9th_hackathon.daybreak.global.entity.BaseEntity;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @Table(name = "category")
    public class Category extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        private Long categoryId;

        @Column(name = "category_name", length = 10, nullable = false)
        private String categoryName;


        // 연관 관계
        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<MissionSelection> missionSelections = new ArrayList<>();
    }