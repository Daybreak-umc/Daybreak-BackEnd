package umc9th_hackathon.daybreak.domain.mission.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc9th_hackathon.daybreak.domain.member.entity.Member;
import umc9th_hackathon.daybreak.domain.mission.entity.Category;
import umc9th_hackathon.daybreak.domain.mission.entity.MissionSelection;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

public interface MissionSelectionRepository extends JpaRepository<MissionSelection, Long> {

    // weekly_missions에서 쓰는 조회 (Optional 1개)
    Optional<MissionSelection> findByMember_MemberId(Long memberId);

    Optional<MissionSelection> findByMember_Email(String email);

    // develop 쪽에서 쓰는 fetch join 조회 (List)
    @Query("""
        SELECT DISTINCT s
        FROM MissionSelection s
        JOIN FETCH s.memberMissions m
        JOIN FETCH s.category c
        WHERE s.member.memberId = :memberId
    """)
    List<MissionSelection> findByMemberIdWithMissionAndCategory(@Param("memberId") Long memberId);

    Optional<MissionSelection> findByMemberAndCategoryAndObjective(
            Member member, Category category, String objective);

    // 유저별 목표 개수 조회
    long countByMember(Member member);



    /**
     * MissionSelection 생성 시각(createTime) 기준으로 오래된 selection 삭제
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
        delete from MissionSelection ms
        where ms.createTime < :cutoff
    """)
    int deleteOldSelections(@Param("cutoff") LocalDateTime cutoff);

}
