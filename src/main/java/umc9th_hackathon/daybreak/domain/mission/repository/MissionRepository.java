package umc9th_hackathon.daybreak.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc9th_hackathon.daybreak.domain.mission.entity.Mission;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findAllByMissionSelection(MissionSelection missionSelection);

    void deleteAllByMissionSelection(MissionSelection missionSelection);
}

