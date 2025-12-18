package umc9th_hackathon.daybreak.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc9th_hackathon.daybreak.domain.mission.entity.Mission;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    void deleteByMissionSelection(MissionSelection missionSelection);
}
