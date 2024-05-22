package wepik.backend.module.template.dao;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import wepik.backend.global.common.BaseTimeEntity;
import wepik.backend.module.file.File;
import wepik.backend.module.question.dao.Question;
import wepik.backend.module.template.dto.TemplateTagDto;

@Entity
@Getter
@Builder
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

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<TemplateTag> templateTags = new ArrayList<>();

    @OneToMany(mappedBy = "template")
    private List<MemTempMapping> memTempMappings = new ArrayList<>();
}
