package wepik.backend.module.result.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Getter;
import wepik.backend.module.template.dao.Template;

@Getter
@Entity
public class Result {

    @Id
    @Column(name = "result_uuid", unique = true, nullable = false)
    private String uuid;

    @OneToMany(mappedBy = "result")
    @Column(name = "answer_id", nullable = true)
    private List<Answer> answers;

    @OneToOne
    private Template template;
}
