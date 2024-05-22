package wepik.backend.module.result.dao;

import jakarta.persistence.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import wepik.backend.module.template.dao.Template;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

    @Id
    @Column(name = "result_id")
    @JdbcType(VarcharJdbcType.class)
    private String id;

    @OneToMany(mappedBy = "result")
    @Column(name = "answer_id", nullable = true)
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;
}
