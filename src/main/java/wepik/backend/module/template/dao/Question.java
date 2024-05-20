package wepik.backend.module.template.dao;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wepik.backend.global.common.BaseTimeEntity;
import wepik.backend.module.file.File;
import wepik.backend.module.member.dao.Member;
import wepik.backend.module.result.dao.Answer;

@Entity
@Getter
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
    private int questionSequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToOne
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
