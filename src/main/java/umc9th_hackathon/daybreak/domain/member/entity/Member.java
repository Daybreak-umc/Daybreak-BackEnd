package umc9th_hackathon.daybreak.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc9th_hackathon.daybreak.global.auth.enums.Role;
import umc9th_hackathon.daybreak.global.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
