package umc9th_hackathon.daybreak.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import umc9th_hackathon.daybreak.domain.mission.entity.Plan;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByMissionSelection(MissionSelection missionSelection);
    void deleteByMissionSelection(MissionSelection missionSelection);
    Optional<Plan> findByMissionSelection_MissionSelectionId(Long missionSelectionId);
}
