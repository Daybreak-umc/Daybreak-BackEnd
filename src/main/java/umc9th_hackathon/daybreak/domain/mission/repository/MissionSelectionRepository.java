package umc9th_hackathon.daybreak.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

public interface MissionSelectionRepository extends JpaRepository<MissionSelection, Long> {
<<<<<<< HEAD
    @Query("""
    SELECT DISTINCT s
    FROM MissionSelection s
    JOIN FETCH s.memberMissions m
    JOIN FETCH s.category c
    WHERE s.member.memberId = :memberId
=======

    // weekly_missions에서 쓰는 조회 (Optional 1개)
    Optional<MissionSelection> findByMember_MemberId(Long memberId);

    // develop 쪽에서 쓰는 fetch join 조회 (List)
    @Query("""
        SELECT DISTINCT s
        FROM MissionSelection s
        JOIN FETCH s.memberMissions m
        JOIN FETCH s.category c
        WHERE s.member.memberId = :memberId
>>>>>>> parent of a0d311e (Revert "[Feat] 주간 미션 3개 생성 API 구현 (OpenAI 연동)")
    """)
    List<MissionSelection> findByMemberIdWithMissionAndCategory(@Param("memberId") Long memberId);
    Optional<MissionSelection> findByMember_MemberId(Long memberId);

}