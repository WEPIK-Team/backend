package wepik.backend.module.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.File;
import wepik.backend.module.template.dao.AnswerType;
import wepik.backend.module.template.dao.Question;

@Data
@Builder
@AllArgsConstructor
public class QuestionRequest {

    private String title;

    private AnswerType type;

    private Integer questionSequence;

    private File file;

    public Question toEntity() {
        return Question.builder()
                .title(title)
                .type(type)
                .questionSequence(questionSequence)
                .build();
    }
}
