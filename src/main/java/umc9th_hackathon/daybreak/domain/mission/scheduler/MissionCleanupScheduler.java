package umc9th_hackathon.daybreak.domain.mission.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionRepository;
import umc9th_hackathon.daybreak.domain.mission.repository.MissionSelectionRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MissionCleanupScheduler {

    private final MissionRepository missionRepository;
    private final MissionSelectionRepository missionSelectionRepository;


    //한국 기준 새벽 4시에 삭제
    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    @Transactional
    public void cleanupOldMissionsAndSelections() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(7); //created시간 기준으로 7일 후

        int deletedMissions = missionRepository.deleteOldMissionsBySelectionCreateTime(cutoff);
        int deletedSelections = missionSelectionRepository.deleteOldSelections(cutoff);

        log.info("[MISSION-CLEANUP] cutoff={}, deletedMissions={}, deletedSelections={}",
                cutoff, deletedMissions, deletedSelections);
    }
}
