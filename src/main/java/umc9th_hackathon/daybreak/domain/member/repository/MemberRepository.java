package umc9th_hackathon.daybreak.domain.member.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc9th_hackathon.daybreak.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(@Email @NotBlank String email);

    Optional<Object> findByEmail(String email);
}
