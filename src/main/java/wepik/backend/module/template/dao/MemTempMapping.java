package wepik.backend.module.template.dao;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wepik.backend.module.member.dao.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemTempMapping {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_temp_mapping_id")
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Template template;
}
