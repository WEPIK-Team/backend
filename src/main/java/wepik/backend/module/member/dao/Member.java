package wepik.backend.module.member.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
@Table(name = "MEMBER")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String nickname;

    @Column
    @Email
    private String email;

    @Column
    private String password;

    @Column(name = "CREATED_AT")
    private LocalDateTime createAT;

    @Column
    private Boolean active;

    @Column
    private String provider;
}
