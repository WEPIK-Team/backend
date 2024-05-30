package wepik.backend.module.result.dao;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wepik.backend.global.common.BaseTimeEntity;
import wepik.backend.module.question.dao.Question;
import wepik.backend.module.question.dao.AnswerType;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Answer extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "sequence")
    private int sequence;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AnswerType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id")
    private Result result;

    public void setResult(Result result) {
        this.result = result;
    }
}
