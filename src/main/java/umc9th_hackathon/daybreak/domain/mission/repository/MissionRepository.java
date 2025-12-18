package umc9th_hackathon.daybreak.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc9th_hackathon.daybreak.domain.mission.entity.Mission;

@Repository
public interface MissionRepository  extends JpaRepository<Mission, Long> {
}
