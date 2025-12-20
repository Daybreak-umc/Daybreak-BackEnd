package umc9th_hackathon.daybreak.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.mission.entity.Mission;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    void deleteByMissionSelection(MissionSelection missionSelection);

    Optional<Mission> findByMissionIdAndMissionSelection_Member_Email(
            Long missionId,
            String email
    );

    /**
     * MissionSelection 생성 시각(createTime) 기준으로 오래된 selection에 속한 미션 삭제
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
        delete from Mission m
        where m.missionSelection.missionSelectionId in (
            select ms.missionSelectionId
            from MissionSelection ms
            where ms.createTime < :cutoff
        )
    """)
    int deleteOldMissionsBySelectionCreateTime(@Param("cutoff") LocalDateTime cutoff);
}
