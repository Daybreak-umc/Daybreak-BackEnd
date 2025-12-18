package umc9th_hackathon.daybreak.domain.mission.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;

public interface MissionSelectionRepository extends JpaRepository<MissionSelection, Long> {
    Optional<MissionSelection> findByMember_MemberId(Long memberId);
}

