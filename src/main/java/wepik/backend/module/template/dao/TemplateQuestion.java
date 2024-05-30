package wepik.backend.module.template.dao;

import jakarta.persistence.*;
import lombok.*;
import wepik.backend.module.question.dao.Question;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "template_question")
public class TemplateQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    public void addTemplate(Template template) {
        this.template = template;
    }
    public Question getQuestion() {
        return question;
    }
}

