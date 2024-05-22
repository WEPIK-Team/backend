package wepik.backend.module.template.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import wepik.backend.global.common.BaseTimeEntity;
import wepik.backend.module.file.File;
import wepik.backend.module.result.dao.Answer;
import wepik.backend.module.result.dao.Result;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Template extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @PositiveOrZero
    @ColumnDefault("0")
    @Column(nullable = false)
    private int useCount;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @OneToMany(mappedBy = "template")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "template")
    private List<TemplateTag> templateTags = new ArrayList<>();

    @OneToMany(mappedBy = "template")
    private List<MemTempMapping> memTempMappings = new ArrayList<>();

    @OneToMany(mappedBy = "template")
    private List<Result> results = new ArrayList<>();
}
