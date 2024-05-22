package wepik.backend.module.question.dao;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import wepik.backend.global.common.BaseTimeEntity;
import wepik.backend.module.file.File;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.result.dao.Answer;
import wepik.backend.module.template.dao.Template;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnswerType type;

    @Column(nullable = true)
    private Integer questionSequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<SelectQuestion> selectQuestions = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

}
