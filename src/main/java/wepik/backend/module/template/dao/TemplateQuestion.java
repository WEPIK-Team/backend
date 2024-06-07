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
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public void setTemplate(Template template) {
        this.template = template;
    }
    public static TemplateQuestion createTemplateQuestion(Template template, Question question) {
        TemplateQuestion templateQuestion = new TemplateQuestion();
        templateQuestion.template = template;
        templateQuestion.question = question;
        return templateQuestion;
    }
}

